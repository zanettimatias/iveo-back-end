package ar.com.mzanetti.iveo.business;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.tomcat.util.codec.binary.Base64;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

@Service
public class DescriptorBusinessImpl implements DescriptorsBusiness{

    public String matToJson(Mat mat){
        JsonObject obj = new JsonObject();

        if(mat.isContinuous()){
            int cols = mat.cols();
            int rows = mat.rows();
            int elemSize = (int) mat.elemSize();
            int type = mat.type();

            obj.addProperty("rows", rows);
            obj.addProperty("cols", cols);
            obj.addProperty("type", type);

            // We cannot set binary data to a json object, so:
            // Encoding data byte array to Base64.
            String dataString;

            if( type == CvType.CV_32S || type == CvType.CV_32SC2 || type == CvType.CV_32SC3 || type == CvType.CV_16S) {
                int[] data = new int[cols * rows * elemSize];
                mat.get(0, 0, data);
                dataString = new String(Base64.encodeBase64(SerializationUtils.serialize(data)));
            }
            else if( type == CvType.CV_32F || type == CvType.CV_32FC2) {
                float[] data = new float[cols * rows * elemSize];
                mat.get(0, 0, data);
                dataString = new String(Base64.encodeBase64(SerializationUtils.serialize(data)));
            }
            else if( type == CvType.CV_64F || type == CvType.CV_64FC2) {
                double[] data = new double[cols * rows * elemSize];
                mat.get(0, 0, data);
                dataString = new String(Base64.encodeBase64(SerializationUtils.serialize(data)));
            }
            else if( type == CvType.CV_8U ) {
                byte[] data = new byte[cols * rows * elemSize];
                mat.get(0, 0, data);
                dataString = new String(Base64.encodeBase64(data));
            }
            else {

                throw new UnsupportedOperationException("unknown type");
            }
            obj.addProperty("data", dataString);

            Gson gson = new Gson();
            String json = gson.toJson(obj);

            return json;
        } else {
            System.out.println("Mat not continuous.");
        }
        return "{}";
    }

    public Mat matFromJson(String json){


        JsonParser parser = new JsonParser();
        JsonObject JsonObject = parser.parse(json).getAsJsonObject();

        int rows = JsonObject.get("rows").getAsInt();
        int cols = JsonObject.get("cols").getAsInt();
        int type = JsonObject.get("type").getAsInt();

        Mat mat = new Mat(rows, cols, type);

        String dataString = JsonObject.get("data").getAsString();
        if( type == CvType.CV_32S || type == CvType.CV_32SC2 || type == CvType.CV_32SC3 || type == CvType.CV_16S) {
            int[] data = (int[]) SerializationUtils.deserialize(Base64.decodeBase64(dataString.getBytes()));
            mat.put(0, 0, data);
        }
        else if( type == CvType.CV_32F || type == CvType.CV_32FC2) {
            float[] data = (float[]) SerializationUtils.deserialize(Base64.decodeBase64(dataString.getBytes()));
            mat.put(0, 0, data);
        }
        else if( type == CvType.CV_64F || type == CvType.CV_64FC2) {
            double[] data = (double[]) SerializationUtils.deserialize(Base64.decodeBase64(dataString.getBytes()));
            mat.put(0, 0, data);
        }
        else if( type == CvType.CV_8U ) {
            byte[] data = Base64.decodeBase64(dataString.getBytes());
            mat.put(0, 0, data);
        }
        else {

            throw new UnsupportedOperationException("unknown type");
        }
        return mat;
    }

}

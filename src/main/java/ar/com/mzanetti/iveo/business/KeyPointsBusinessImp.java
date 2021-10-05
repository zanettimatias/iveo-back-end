package ar.com.mzanetti.iveo.business;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.opencv.core.KeyPoint;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Point;
import org.springframework.stereotype.Service;

@Service
public class KeyPointsBusinessImp implements KeyPointsBusiness {
    @Override
    public String keypointsToJson(MatOfKeyPoint mat) {
        if (mat != null && !mat.empty()) {
            Gson gson = new Gson();
            JsonArray jsonArr = new JsonArray();

            KeyPoint[] array = mat.toArray();
            for (KeyPoint kp : array) {
                JsonObject obj = new JsonObject();
                obj.addProperty("class_id", kp.class_id);
                obj.addProperty("x", kp.pt.x);
                obj.addProperty("y", kp.pt.y);
                obj.addProperty("size", kp.size);
                obj.addProperty("angle", kp.angle);
                obj.addProperty("octave", kp.octave);
                obj.addProperty("response", kp.response);
                jsonArr.add(obj);
            }
            return gson.toJson(jsonArr);
        }
        return "{}";
    }

    @Override
    public MatOfKeyPoint keypointsFromJson(String json) {
        MatOfKeyPoint result = new MatOfKeyPoint();

        JsonArray jsonArr = JsonParser.parseString(json).getAsJsonArray();
        int size = jsonArr.size();
        KeyPoint[] kpArray = new KeyPoint[size];

        for (int i = 0; i < size; i++) {
            KeyPoint kp = new KeyPoint();

            JsonObject obj = (JsonObject) jsonArr.get(i);

            kp.pt = new Point(
                    obj.get("x").getAsDouble(),
                    obj.get("y").getAsDouble()
            );
            kp.class_id = obj.get("class_id").getAsInt();
            kp.size = obj.get("size").getAsFloat();
            kp.angle = obj.get("angle").getAsFloat();
            kp.octave = obj.get("octave").getAsInt();
            kp.response = obj.get("response").getAsFloat();
            kpArray[i] = kp;
        }
        result.fromArray(kpArray);
        return result;
    }

}

package ar.com.mzanetti.iveo.service;


import ar.com.mzanetti.iveo.dto.ObjectDetectionResult;
import org.opencv.core.*;
import org.opencv.dnn.Net;
import org.opencv.utils.Converters;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.opencv.dnn.Dnn.*;


@Service
public class YoloNetServiceImpl implements YoloNetService {

    private Path configPath;
    private Path weightsPath;
    private Path namesPath;
    private int width;
    private int height;

    private Net net;
    private List<String> outNames;

    private List<String> names;

    @Value("${yolo.url}")
    String url;
    @Value("${yolo.weights}")
    String weights;
    @Value("${yolo.cfg}")
    String cfg;
    @Value("${coco.names}")
    String cocoNames;

    @Bean
    public void create() {
        this.configPath = Paths.get(url + File.separator + cfg);
        this.weightsPath = Paths.get(url + File.separator + weights);
        this.namesPath = Paths.get(url + File.separator + cocoNames);
        this.width = 608;
        this.height = 608;
        this.setup();
    }


    /**
     * Initialises the network.
     */
    public void setup() {

        net = readNetFromDarknet(
                configPath.toAbsolutePath().toString(),
                weightsPath.toAbsolutePath().toString());
        outNames = net.getUnconnectedOutLayersNames();
        try {
            names = Files.readAllLines(namesPath);
        } catch (IOException e) {
            System.err.println("No se encontraron los nombre dentro del archivo coco.names");
            e.printStackTrace();
        }
        net.empty();
    }

    /**
     * Metodo para detectar los objecto con YOLO
     *
     * @param frame Input frame.
     * @return List of ObjectDetectionResult
     */
    public List<ObjectDetectionResult> predict(Mat frame) {
        Mat inputBlob = blobFromImage(frame,
                1 / 255.0,
                new Size(width, height),
                new Scalar(0),
                true, false);

        net.setInput(inputBlob);
        List<Mat> outs = new ArrayList();
        net.forward(outs, outNames);
        List<ObjectDetectionResult> result = postprocess(frame, outs);
        inputBlob.release();
        result.forEach(objectDetectionResult -> System.out.println(objectDetectionResult.getClassName()));
        return result;
    }

    /**
     * Metodo para encontrar los objetos YOLO con el menor valor de confindencia
     *
     * @param frame Input frame
     * @param outs  List of Mat
     * @return List of objectDetectionResult
     */
    private List<ObjectDetectionResult> postprocess(Mat frame, List<Mat> outs) {
        final List<Integer> classIds = new ArrayList<>();
        final List<Float> confidences = new ArrayList<>();
        final List<Rect2d> boxes = new ArrayList<>();

        float confidenceThreshold = 0.5f;
        for (Mat result : outs) {
            for (int j = 0; j < result.rows(); j++) {
                Mat row = result.row(j);
                Mat scores = row.colRange(5, result.cols());
                Core.MinMaxLocResult mm = Core.minMaxLoc(scores);
                float confidence = (float) mm.maxVal;
                Point classIdPoint = mm.maxLoc;

                if (confidence > confidenceThreshold) {
                    int centerX = (int) (row.get(0, 0)[0] * frame.cols());
                    int centerY = (int) (row.get(0, 1)[0] * frame.rows());
                    int width = (int) (row.get(0, 2)[0] * frame.cols());
                    int height = (int) (row.get(0, 3)[0] * frame.rows());
                    int left = centerX - width / 2;
                    int top = centerY - height / 2;

                    classIds.add((int) classIdPoint.x);
                    confidences.add((float) confidence);
                    boxes.add(new Rect2d(left, top, width, height));
                }
            }

        }

        // remove overlapping bounding boxes with NMS
        MatOfInt indices = new MatOfInt(confidences.size());
        MatOfFloat confidencesPointer = new MatOfFloat(Converters.vector_float_to_Mat(confidences));
        // confidencesPointer.put(new int[]{confidences.size()});

        MatOfRect2d matOfBoxes = new MatOfRect2d();
        matOfBoxes.fromList(boxes);

        float nmsThreshold = 0.4f;
        NMSBoxes(matOfBoxes, confidencesPointer, confidenceThreshold, nmsThreshold, indices, 1.f, 0);

        // create result list
        List<ObjectDetectionResult> detections = new ArrayList<>();
        int[] ind = indices.toArray();
        for (final int idx : ind) {
            final Rect2d box = boxes.get(idx);
            final int clsId = classIds.get(idx);

            detections.add(new ObjectDetectionResult(clsId,
                    names.get(clsId),
                    confidences.get(idx),
                    box.x,
                    box.y,
                    box.width,
                    box.height
            ));
        }
        return detections;
    }
}

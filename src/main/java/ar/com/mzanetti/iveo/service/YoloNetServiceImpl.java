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


  /*  *//**
     * Creates a new YOLO network.
     *
     * @param configPath  Path to the configuration file.
     * @param weightsPath Path to the weights file.
     * @param namesPath   Path to the names file.
     * @param width       Width of the network as defined in the configuration.
     * @param height      Height of the network as defined in the configuration.
     *//*
    public YoloNetServiceImpl(String configPath, String weightsPath, String namesPath, int width, int height) {
        this.configPath = Paths.get(configPath);
        this.weightsPath = Paths.get(weightsPath);
        this.namesPath = Paths.get(namesPath);
        this.width = width;
        this.height = height;
    }*/

    /**
     * Initialises the network.
     *
     * @return True if the network initialisation was successful.
     */
    public boolean setup() {

        net = readNetFromDarknet(
                configPath.toAbsolutePath().toString(),
                weightsPath.toAbsolutePath().toString());

        // setup output layers
        outNames = net.getUnconnectedOutLayersNames();

        // read names file
        try {
            names = Files.readAllLines(namesPath);
        } catch (IOException e) {
            System.err.println("Could not read names file!");
            e.printStackTrace();
        }

        return !net.empty();
    }

    /**
     * Runs the object detection on the frame.
     *
     * @param frame Input frame.
     * @return List of objects that have been detected.
     */
    public List<ObjectDetectionResult> predict(Mat frame) {
        Mat inputBlob = blobFromImage(frame,
                1 / 255.0,
                new Size(width, height),
                new Scalar(0),
                true, false);

        net.setInput(inputBlob);

        // run detection
        List<Mat> outs = new ArrayList();
        net.forward(outs, outNames);

        // evaluate result
        List<ObjectDetectionResult> result = postprocess(frame, outs);

        // cleanup
        inputBlob.release();

        return result;
    }

    /**
     * Remove the bounding boxes with low confidence using non-maxima suppression
     *
     * @param frame Input frame
     * @param outs  Network outputs
     * @return List of objects
     */
    private List<ObjectDetectionResult> postprocess(Mat frame, List<Mat> outs) {
        final List<Integer> classIds = new ArrayList<>();
        final List<Float> confidences = new ArrayList<>();
        final List<Rect2d> boxes = new ArrayList<>();

        float confidenceThreshold = 0.5f;
        for (int i = 0; i < outs.size(); ++i) {
            // extract the bounding boxes that have a high enough score
            // and assign their highest confidence class prediction.
            Mat result = outs.get(i);

            for (int j = 0; j < result.rows(); j++) {
                // minMaxLoc implemented in java because it is 1D
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
        for (int i = 0; i < ind.length; ++i) {
            final int idx = ind[i];
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

    /**
     * Dataclass for object detection result.
     */


}

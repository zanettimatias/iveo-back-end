package ar.com.mzanetti.iveo.service;

import ar.com.mzanetti.iveo.dto.ObjectDetectionResult;
import org.opencv.core.Mat;

import java.util.List;

public interface YoloNetService {
    public List<ObjectDetectionResult> predict(Mat frame);
}

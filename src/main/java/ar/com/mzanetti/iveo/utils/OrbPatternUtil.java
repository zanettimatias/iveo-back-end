package ar.com.mzanetti.iveo.utils;

import org.bson.json.JsonObject;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;

import java.util.List;

public class OrbPatternUtil {

    private MatOfKeyPoint keyPoints;
    private Mat descriptors;
    private List<Integer> yoloClasses;

    public OrbPatternUtil(MatOfKeyPoint keyPoints, Mat descriptors) {
        this.keyPoints = keyPoints;
        this.descriptors = descriptors;
    }

    public MatOfKeyPoint getKeyPoints() {
        return keyPoints;
    }

    public void setKeyPoints(MatOfKeyPoint keyPoints) {
        this.keyPoints = keyPoints;
    }

    public Mat getDescriptors() {
        return descriptors;
    }

    public void setDescriptors(Mat descriptors) {
        this.descriptors = descriptors;
    }

    public List<Integer> getYoloClasses() {
        return yoloClasses;
    }

    public void setYoloClasses(List<Integer> yoloClasses) {
        this.yoloClasses = yoloClasses;
    }
}

package ar.com.mzanetti.iveo.utils;

import org.bson.json.JsonObject;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;

public class OrbPatternUtil {

    private MatOfKeyPoint keyPoints;
    private Mat descriptors;

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
}

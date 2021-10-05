package ar.com.mzanetti.iveo.service;

import ar.com.mzanetti.iveo.utils.OrbPatternUtil;
import org.opencv.core.Mat;

public interface CompareInterface {
    public int compare(Mat img1, Mat img2);
    public OrbPatternUtil getKeyPointsAndDescriptor(Mat img);
    public int compareDescriptors(Mat descriptors1, Mat descriptors2);
}

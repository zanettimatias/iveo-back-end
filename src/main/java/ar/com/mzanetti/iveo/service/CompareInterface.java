package ar.com.mzanetti.iveo.service;

import org.opencv.core.Mat;

public interface CompareInterface {
    public int compare(Mat img1, Mat img2);
}

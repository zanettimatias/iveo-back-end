package ar.com.mzanetti.iveo.service;

import jdk.internal.org.jline.reader.Candidate;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.AKAZE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompareTwoImages {

    @Autowired
    CompareFactory factory;

    public Mat compare(Mat img1, Mat img2) {

        Mat desc1 = new Mat(), desc2 = new Mat();

        return null;

    }


}

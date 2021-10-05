package ar.com.mzanetti.iveo.business;

import org.opencv.core.MatOfKeyPoint;

public interface KeyPointsBusiness {

    String keypointsToJson(MatOfKeyPoint mat);
    MatOfKeyPoint keypointsFromJson(String json);

}

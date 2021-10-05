package ar.com.mzanetti.iveo.business;

import org.opencv.core.Mat;

public interface DescriptorsBusiness {
    public String matToJson(Mat mat);
    public Mat matFromJson(String json);
}

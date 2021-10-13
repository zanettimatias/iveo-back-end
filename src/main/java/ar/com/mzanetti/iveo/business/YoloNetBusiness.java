package ar.com.mzanetti.iveo.business;

import ar.com.mzanetti.iveo.persistence.Imagen;
import org.opencv.core.Mat;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public interface YoloNetBusiness {
    List<Integer> getClassFounded(Imagen imagen) throws IOException;
    List<Integer> getClassFounded(Mat imagen) throws IOException;
}

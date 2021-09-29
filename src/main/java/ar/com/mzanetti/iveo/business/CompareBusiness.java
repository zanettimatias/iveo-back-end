package ar.com.mzanetti.iveo.business;

import ar.com.mzanetti.iveo.persistence.Producto;
import org.opencv.core.Mat;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public interface CompareBusiness {
    public int transformAndCompare(BufferedImage match, byte[] img2) throws IOException;
}

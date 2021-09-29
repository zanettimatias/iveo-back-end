package ar.com.mzanetti.iveo.business;

import ar.com.mzanetti.iveo.persistence.Producto;
import ar.com.mzanetti.iveo.repository.ProductoRepository;
import ar.com.mzanetti.iveo.service.CompareInterface;
import ar.com.mzanetti.iveo.service.CriterioDeAceptacion;
import ar.com.mzanetti.iveo.service.ORBFlannPatternFactory;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompareBusinessImpl implements CompareBusiness {

    @Autowired
    CompareInterface compareInterface;

    public int transformAndCompare(BufferedImage match, byte[] img2) throws IOException {
        InputStream is = new ByteArrayInputStream(img2);
        BufferedImage bImage = ImageIO.read(is);
        return compareInterface.compare(convertMat(match), convertMat(bImage));

    }


    /**
     * bufferedImage convert mat
     * @param im
     * @return
     */
    public static Mat convertMat(BufferedImage im) {
        // Convert INT to BYTE
        im = toBufferedImageOfType(im, BufferedImage.TYPE_3BYTE_BGR);
        // Convert bufferedimage to byte array
        byte[] pixels = ((DataBufferByte) im.getRaster().getDataBuffer())
                .getData();
        // Create a Matrix the same size of image
        Mat image = new Mat(im.getHeight(), im.getWidth(), 16);
        // Fill Matrix with image values
        image.put(0, 0, pixels);
        return image;
    }

    /**
     *  8-bit RGBA convert 8-bit RGB
     * @param original
     * @param type
     * @return
     */
    private static BufferedImage toBufferedImageOfType(BufferedImage original, int type) {
        if (original == null) {
            throw new IllegalArgumentException("original == null");
        }

        // Don't convert if it already has correct type
        if (original.getType() == type) {
            return original;
        }
        // Create a buffered image
        BufferedImage image = new BufferedImage(original.getWidth(), original.getHeight(), type);
        // Draw the image onto the new buffer
        Graphics2D g = image.createGraphics();
        try {
            g.setComposite(AlphaComposite.Src);
            g.drawImage(original, 0, 0, null);
        } finally {
            g.dispose();
        }

        return image;
    }

}

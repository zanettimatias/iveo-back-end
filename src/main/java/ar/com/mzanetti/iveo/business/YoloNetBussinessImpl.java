package ar.com.mzanetti.iveo.business;

import ar.com.mzanetti.iveo.dto.ObjectDetectionResult;
import ar.com.mzanetti.iveo.persistence.Imagen;
import ar.com.mzanetti.iveo.service.YoloNetService;
import ar.com.mzanetti.iveo.service.YoloNetServiceImpl;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class YoloNetBussinessImpl implements YoloNetBusiness {

    @Autowired
    YoloNetService yoloNetService;
    @Autowired
    CompareBusiness compareBusiness;

    @Override
    public List<Integer> getClassFounded(Imagen imagen) throws IOException {
        return yoloNetService.predict(compareBusiness.transformToMatByte(
                imagen.getImage().getData())).stream().map(ObjectDetectionResult -> {
            System.out.println(ObjectDetectionResult.getClassName());
            return ObjectDetectionResult.getClassId();
        }).collect(Collectors.toList());
    }
    @Override
    public List<Integer> getClassFounded(Mat imagen) {
        return yoloNetService.predict(imagen).stream().map(ObjectDetectionResult -> {
            System.out.println(ObjectDetectionResult.getClassName());
            return ObjectDetectionResult.getClassId();
        }).collect(Collectors.toList());
    }
}

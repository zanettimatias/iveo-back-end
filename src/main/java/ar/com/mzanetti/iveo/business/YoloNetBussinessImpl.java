package ar.com.mzanetti.iveo.business;

import ar.com.mzanetti.iveo.dto.ObjectDetectionResult;
import ar.com.mzanetti.iveo.persistence.Imagen;
import ar.com.mzanetti.iveo.service.YoloNetService;
import ar.com.mzanetti.iveo.service.YoloNetServiceImpl;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class YoloNetBussinessImpl implements YoloNetBusiness {

    @Autowired
    YoloNetService yoloNetService;

    @Override
    public List<Integer> getClassFounded(Imagen imagen) {
        Mat img1 = Imgcodecs.imread("C:\\Users\\Usuario\\IdeaProjects\\iveo-backend\\src\\test\\resources\\bd\\1.png");

        return yoloNetService.predict(img1).stream().map(ObjectDetectionResult::getClassId).collect(Collectors.toList());
    }
}

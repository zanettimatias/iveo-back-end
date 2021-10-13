package ar.com.mzanetti.iveo.business;

import ar.com.mzanetti.iveo.persistence.Imagen;
import ar.com.mzanetti.iveo.service.ORBFlannPatternFactory;
import ar.com.mzanetti.iveo.utils.OrbPatternUtil;
import org.opencv.core.Mat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.IOException;

@Service
public class ORBFlannPatternBusinessImp implements ORBFlannPatternBusiness {

    @Autowired
    CompareBusiness compareBusiness;
    @Autowired
    ORBFlannPatternFactory orbFlannPatternFactory;
    @Autowired
    YoloNetBusiness yoloNetBusiness;

    public OrbPatternUtil procesarImagenORB(Imagen imagen) throws IOException {
        return orbFlannPatternFactory.getKeyPointsAndDescriptor(compareBusiness.transformToMatByte(imagen.getImage().getData()));
    }
    public OrbPatternUtil procesarImgBufferedORB(BufferedImage imagen) throws IOException {
        Mat img = compareBusiness.transformToMatBufferedImage(imagen);
        OrbPatternUtil orbPatternUtil =  orbFlannPatternFactory.getKeyPointsAndDescriptor(img);
        //orbPatternUtil.setYoloClasses(yoloNetBusiness.getClassFounded(img));
        return orbPatternUtil;
    }

}

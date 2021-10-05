package ar.com.mzanetti.iveo.business;

import ar.com.mzanetti.iveo.persistence.Imagen;
import ar.com.mzanetti.iveo.service.ORBFlannPatternFactory;
import ar.com.mzanetti.iveo.utils.OrbPatternUtil;
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

    public OrbPatternUtil procesarImagenORB(Imagen imagen) throws IOException {
        return orbFlannPatternFactory.getKeyPointsAndDescriptor(compareBusiness.transformToMatByte(imagen.getImage().getData()));
    }
    public OrbPatternUtil procesarImgBufferedORB(BufferedImage imagen) throws IOException {
        return orbFlannPatternFactory.getKeyPointsAndDescriptor(compareBusiness.transformToMatBufferedImage(imagen));
    }

}

package ar.com.mzanetti.iveo.business;

import ar.com.mzanetti.iveo.persistence.Imagen;
import ar.com.mzanetti.iveo.utils.OrbPatternUtil;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface ORBFlannPatternBusiness {
    OrbPatternUtil procesarImagenORB(Imagen imagen) throws IOException;
    public OrbPatternUtil procesarImgBufferedORB(BufferedImage imagen) throws IOException;
}

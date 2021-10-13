package ar.com.mzanetti.iveo.business;

import ar.com.mzanetti.iveo.dto.ImageFoundDto;
import ar.com.mzanetti.iveo.utils.OrbPatternUtil;

public interface AprendizajeBusiness {
    void aprender(OrbPatternUtil match, ImageFoundDto imgFound);
}

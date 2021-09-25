package ar.com.mzanetti.iveo.business;

import ar.com.mzanetti.iveo.persistence.Imagen;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;

public interface YoloNetBusiness {
    List<Integer> getClassFounded(Imagen imagen);
}

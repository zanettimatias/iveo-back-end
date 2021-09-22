package ar.com.mzanetti.iveo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CriterioDeAceptacion {

    @Value("${cant.criterio}")
    Integer cant;

    public Boolean criterio(int matches) {
      return matches > cant;
    }

}

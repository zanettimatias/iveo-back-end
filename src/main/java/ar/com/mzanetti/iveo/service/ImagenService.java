package ar.com.mzanetti.iveo.service;

import ar.com.mzanetti.iveo.persistence.Imagen;

import java.util.List;

public interface ImagenService {
    List<Imagen> findAll() throws Exception;
}

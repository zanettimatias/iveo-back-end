package ar.com.mzanetti.iveo.service;

import ar.com.mzanetti.iveo.persistence.Producto;

public interface ProductoService {
    Producto save(Producto producto) throws Exception;
}

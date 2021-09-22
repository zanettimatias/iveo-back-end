package ar.com.mzanetti.iveo.service;

import ar.com.mzanetti.iveo.dto.ProductoDto;
import ar.com.mzanetti.iveo.persistence.Producto;

public interface DtoService {

    public Producto producto(ProductoDto dto) throws Exception;

}

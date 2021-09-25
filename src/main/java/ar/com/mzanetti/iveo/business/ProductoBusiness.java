package ar.com.mzanetti.iveo.business;

import ar.com.mzanetti.iveo.dto.ProductoDto;
import ar.com.mzanetti.iveo.persistence.Producto;

public interface ProductoBusiness {
    Producto save (ProductoDto dto) throws Exception;
    Producto procesar(ProductoDto dto) throws Exception;
}

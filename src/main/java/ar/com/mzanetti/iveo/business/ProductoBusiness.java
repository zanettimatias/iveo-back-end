package ar.com.mzanetti.iveo.business;

import ar.com.mzanetti.iveo.dto.ProductoDto;
import ar.com.mzanetti.iveo.persistence.Producto;
import org.bson.types.ObjectId;
import reactor.core.publisher.Mono;

public interface ProductoBusiness {
    Producto save (ProductoDto dto) throws Exception;
    Producto procesar(ProductoDto dto) throws Exception;
}

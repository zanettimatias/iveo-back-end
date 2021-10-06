package ar.com.mzanetti.iveo.service;

import ar.com.mzanetti.iveo.persistence.Producto;
import org.bson.types.ObjectId;
import reactor.core.publisher.Mono;

public interface ProductoService {
    Producto save(Producto producto) throws Exception;
}

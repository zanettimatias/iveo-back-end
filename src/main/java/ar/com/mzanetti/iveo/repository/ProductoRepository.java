package ar.com.mzanetti.iveo.repository;

import ar.com.mzanetti.iveo.persistence.Producto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductoRepository extends MongoRepository<Producto,String> {
}

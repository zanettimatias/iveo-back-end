package ar.com.mzanetti.iveo.repository;

import ar.com.mzanetti.iveo.persistence.Imagen;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImagenRepository extends MongoRepository<Imagen, String> {
}

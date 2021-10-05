package ar.com.mzanetti.iveo.repository;

import ar.com.mzanetti.iveo.persistence.Imagen;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ImagenRepository extends ReactiveMongoRepository<Imagen, ObjectId> {
}

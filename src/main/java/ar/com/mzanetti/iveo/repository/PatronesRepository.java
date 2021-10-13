package ar.com.mzanetti.iveo.repository;

import ar.com.mzanetti.iveo.persistence.Patrones;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

public interface PatronesRepository extends ReactiveMongoRepository<Patrones, ObjectId> {
    Flux<Patrones> findByUser(String user);
    Flux<Patrones> findByUserNot(String user);
}

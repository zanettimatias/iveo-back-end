package ar.com.mzanetti.iveo.service;

import ar.com.mzanetti.iveo.persistence.Imagen;
import ar.com.mzanetti.iveo.repository.ImagenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class ImagenServiceImpl implements ImagenService {

    @Autowired
    ImagenRepository imagenRepository;

    @Override
    public List<Imagen> findAll() throws Exception {
        return imagenRepository.findAll().collectList().block();
    }
}

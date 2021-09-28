package ar.com.mzanetti.iveo.service;

import ar.com.mzanetti.iveo.persistence.Imagen;
import ar.com.mzanetti.iveo.repository.ImagenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImagenServiceImpl implements ImagenService {

    @Autowired
    ImagenRepository imagenRepository;

    @Override
    public List<Imagen> findAll() throws Exception {
        return imagenRepository.findAll();
    }
}

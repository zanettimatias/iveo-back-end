package ar.com.mzanetti.iveo.business;

import ar.com.mzanetti.iveo.dto.ImageFoundDto;
import ar.com.mzanetti.iveo.persistence.Imagen;
import ar.com.mzanetti.iveo.persistence.Patrones;
import ar.com.mzanetti.iveo.persistence.Producto;
import ar.com.mzanetti.iveo.repository.ImagenRepository;
import ar.com.mzanetti.iveo.repository.PatronesRepository;
import ar.com.mzanetti.iveo.repository.ProductoRepository;
import ar.com.mzanetti.iveo.utils.OrbPatternUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AprendisajeBusinessImpl implements AprendizajeBusiness{

    @Value("${aprendizaje.cant}")
    Integer cant;
    @Autowired
    PatronesRepository patronesRepository;
    @Autowired
    ImagenRepository imagenRepository;
    @Autowired
    ProductoRepository productoRepository;

    @Override
    public void aprender(OrbPatternUtil match, ImageFoundDto imgFound) {
        if(imgFound.getMatch() > cant){
        }
    }
}

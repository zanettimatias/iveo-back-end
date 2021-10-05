package ar.com.mzanetti.iveo.business;

import ar.com.mzanetti.iveo.persistence.Patrones;
import ar.com.mzanetti.iveo.persistence.Producto;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

public interface MatchBusiness {
    Flux<Patrones> getCandidate(MultipartFile img) throws Exception;
}

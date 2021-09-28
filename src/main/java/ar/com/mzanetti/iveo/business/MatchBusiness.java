package ar.com.mzanetti.iveo.business;

import ar.com.mzanetti.iveo.persistence.Producto;
import org.springframework.web.multipart.MultipartFile;

public interface MatchBusiness {
    Producto getCandidato(MultipartFile img) throws Exception;
}

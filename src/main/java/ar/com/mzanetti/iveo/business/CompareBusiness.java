package ar.com.mzanetti.iveo.business;

import ar.com.mzanetti.iveo.persistence.Producto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CompareBusiness {

    public Producto getBestCandidate (MultipartFile multipartFile) throws Exception;

}

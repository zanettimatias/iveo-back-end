package ar.com.mzanetti.iveo.business;

import ar.com.mzanetti.iveo.dto.ImageFoundDto;
import ar.com.mzanetti.iveo.dto.SpeakDto;
import ar.com.mzanetti.iveo.persistence.Patrones;
import ar.com.mzanetti.iveo.persistence.Producto;
import ar.com.mzanetti.iveo.utils.OrbPatternUtil;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.awt.image.BufferedImage;

public interface MatchBusiness {
    Flux<ImageFoundDto> getCandidate(OrbPatternUtil img) throws Exception;
    Flux<ImageFoundDto> getCandidateByUser(OrbPatternUtil img, String user) throws Exception;
}

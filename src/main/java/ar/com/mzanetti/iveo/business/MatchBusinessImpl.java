package ar.com.mzanetti.iveo.business;

import ar.com.mzanetti.iveo.dto.ImageFoundDto;
import ar.com.mzanetti.iveo.dto.SpeakDto;
import ar.com.mzanetti.iveo.persistence.Producto;
import ar.com.mzanetti.iveo.repository.PatronesRepository;
import ar.com.mzanetti.iveo.repository.ProductoRepository;
import ar.com.mzanetti.iveo.service.CriterioDeAceptacion;
import ar.com.mzanetti.iveo.utils.OrbPatternUtil;
import org.opencv.core.Mat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Comparator;

@Service
public class MatchBusinessImpl implements MatchBusiness {

    @Autowired
    CriterioDeAceptacion criterio;
    @Autowired
    CompareBusiness compareBusiness;
    @Autowired
    PatronesRepository patronesRepository;
    @Autowired
    ORBFlannPatternBusiness orbFlannPatternBusiness;
    @Autowired
    DescriptorsBusiness DescriptorsBusiness;
    @Autowired
    ProductoRepository productoRepository;
    @Autowired
    AprendizajeBusiness aprendizajeBusiness;

    @Value("${cant.candidates}")
    long candidates;

    /**
     * Metodo que trae el candidato  con el rango mas alto de puntos encontrados
     */
    public Flux<ImageFoundDto> getCandidate(OrbPatternUtil img) {
        Flux<ImageFoundDto> stream = patronesRepository.findAll().map(patrones -> new ImageFoundDto(patrones.getId(), patrones, patrones.getProductoId())).filter(patrones ->
                compareDescriptors(img.getDescriptors(), DescriptorsBusiness.matFromJson(patrones.getPatrones().getDescriptors()), patrones)
        ).take(candidates).sort(Comparator.comparingLong(ImageFoundDto::getMatch)).take(1);
        return stream;
    }

    /**
     * Metodo que trae el los candidatos, exceptuando a un usuario
     */
    public Flux<ImageFoundDto> getCandidateNotByUser(OrbPatternUtil img, String user) {
        Flux<ImageFoundDto> stream = patronesRepository.findByUserNot(user).map(patrones -> new ImageFoundDto(patrones.getId(), patrones, patrones.getProductoId())).filter(patrones ->
                compareDescriptors(img.getDescriptors(), DescriptorsBusiness.matFromJson(patrones.getPatrones().getDescriptors()), patrones)
        ).take(candidates).sort(Comparator.comparingLong(ImageFoundDto::getMatch)).take(1);
        return stream;
    }

    /**
     * Metodo que trae el candidato  con el rango mas alto de puntos encontrados con el
     * filtro de usuario, esto hace que cada usuario busque primero por los que agrego el
     */
    public Flux<ImageFoundDto> getCandidateByUser(OrbPatternUtil img, String user) throws Exception {
        Flux<ImageFoundDto> stream = patronesRepository.findByUser(user).map(patrones -> new ImageFoundDto(patrones.getId(), patrones, patrones.getProductoId())).filter(patrones ->
                compareDescriptors(img.getDescriptors(), DescriptorsBusiness.matFromJson(patrones.getPatrones().getDescriptors()), patrones)
        ).take(candidates).sort(Comparator.comparingLong(ImageFoundDto::getMatch)).take(1);
        if(stream.blockLast() == null){
            return this.getCandidateNotByUser(img, user);
        }
        return stream;
    }


    /**
     * Se Trabaja la excepcion dentro de Straem de iteracion
     */
    private Boolean handleExcpetionOnTransform(BufferedImage bufferedImage, ImageFoundDto imagen) {
        try {
            imagen.setMatch(compareBusiness.transformAndCompare(bufferedImage, imagen.getImage().getData()));
            return criterio.criterio(imagen.getMatch());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Se Trabaja la excepcion dentro de Straem de iteracion
     */
    private Boolean compareDescriptors(Mat descriptorMatch, Mat descriptorImagen, ImageFoundDto dto) {
        try {
            dto.setMatch(compareBusiness.compareDescriptors(descriptorMatch, descriptorImagen));
            System.out.println(dto.getMatch());
            return criterio.criterio(dto.getMatch());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}

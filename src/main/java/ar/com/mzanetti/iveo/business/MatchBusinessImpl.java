package ar.com.mzanetti.iveo.business;

import ar.com.mzanetti.iveo.dto.ImageFoundDto;
import ar.com.mzanetti.iveo.persistence.Imagen;
import ar.com.mzanetti.iveo.persistence.Patrones;
import ar.com.mzanetti.iveo.persistence.Producto;
import ar.com.mzanetti.iveo.repository.PatronesRepository;
import ar.com.mzanetti.iveo.service.CompareInterface;
import ar.com.mzanetti.iveo.service.CriterioDeAceptacion;
import ar.com.mzanetti.iveo.service.ImagenService;
import ar.com.mzanetti.iveo.utils.OrbPatternUtil;
import org.opencv.core.Mat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.objenesis.SpringObjenesis;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchBusinessImpl implements MatchBusiness {

    @Autowired
    ImagenService imagenService;
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


    /**
     * Metodo que trae el candidato  con el rango mas alto de puntos encontrados
     */
    public Flux<Patrones> getCandidate(MultipartFile img) throws Exception {
        BufferedImage bufferedImage = ImageIO.read(img.getInputStream());
        OrbPatternUtil orbPatternUtil = orbFlannPatternBusiness.procesarImgBufferedORB(bufferedImage);

        Flux<Patrones> stream = patronesRepository.findAll().filter(patrones ->
                compareDescriptors(orbPatternUtil.getDescriptors(), DescriptorsBusiness.matFromJson(patrones.getDescriptors())));
        stream.subscribe(patrones -> {
            System.out.println(patrones.getId());
        });
/*
        List<ImageFoundDto> candidates = imagenes.stream().map(ImageFoundDto::new).filter(imagen -> handleExcpetionOnTransform(bufferedImage, imagen)).collect(Collectors.toList());
        candidates.forEach(imagen -> {
            System.out.println(imagen.getMatch());
        });*/
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
    private Boolean compareDescriptors(Mat descriptorMatch, Mat descriptorImagen) {
        try {
            return criterio.criterio(compareBusiness.compareDescriptors(descriptorMatch, descriptorImagen));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}

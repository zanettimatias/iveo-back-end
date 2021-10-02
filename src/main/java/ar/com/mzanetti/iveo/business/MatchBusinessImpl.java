package ar.com.mzanetti.iveo.business;

import ar.com.mzanetti.iveo.dto.ImageFoundDto;
import ar.com.mzanetti.iveo.persistence.Imagen;
import ar.com.mzanetti.iveo.persistence.Producto;
import ar.com.mzanetti.iveo.service.CriterioDeAceptacion;
import ar.com.mzanetti.iveo.service.ImagenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
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

    /**
     * Metodo que trae el candidato  con el rango mas alto de puntos encontrados
     */
    public Producto getCandidate(MultipartFile img) throws Exception {
        List<Imagen> imagenes = imagenService.findAll();
        BufferedImage bufferedImage = ImageIO.read(img.getInputStream());
        List<ImageFoundDto> candidates = imagenes.stream().map(ImageFoundDto::new).filter(imagen -> handleExcpetionOnTransform(bufferedImage, imagen)).collect(Collectors.toList());
        candidates.forEach(imagen -> {
            System.out.println(imagen.getMatch());
        });
        return null;
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

}

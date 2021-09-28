package ar.com.mzanetti.iveo.business;

import ar.com.mzanetti.iveo.dto.ProductoMatchDto;
import ar.com.mzanetti.iveo.persistence.Imagen;
import ar.com.mzanetti.iveo.persistence.Producto;
import ar.com.mzanetti.iveo.service.CriterioDeAceptacion;
import ar.com.mzanetti.iveo.service.ImagenService;
import ar.com.mzanetti.iveo.service.ORBFlannPatternFactory;
import ar.com.mzanetti.iveo.service.ProductoService;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchBusinessImpl implements MatchBusiness {

    @Autowired
    ProductoService productoService;
    @Autowired
    ImagenService imagenService;
    @Autowired
    CriterioDeAceptacion criterio;
    @Autowired
    ORBFlannPatternFactory orbFlannPatternFactory;


    public Producto getCandidato(MultipartFile img) throws Exception {
        List<Imagen> imagenes = imagenService.findAll();
/*        MatOfByte matchImg =  new MatOfByte(img.getBytes());
        Imgcodecs.imencode(".jpg",new Mat(),matchImg);
        List<File> candidates = imagenes.stream().filter(file -> criterio.criterio(orbFlannPatternFactory.compare(new MatOfByte(file.getImage().getData()), Imgcodecs.imread(matchImg, Imgcodecs.IMREAD_GRAYSCALE)))).collect(Collectors.toList());
        candidates.stream().forEach(file -> {
            System.out.println(file.getAbsolutePath());
        });*/
        return null;
    }
}

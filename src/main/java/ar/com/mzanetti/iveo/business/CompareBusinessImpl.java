package ar.com.mzanetti.iveo.business;

import ar.com.mzanetti.iveo.persistence.Producto;
import ar.com.mzanetti.iveo.repository.ProductoRepository;
import ar.com.mzanetti.iveo.service.CriterioDeAceptacion;
import ar.com.mzanetti.iveo.service.ORBFlannPatternFactory;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class CompareBusinessImpl implements CompareBusiness {

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    ORBFlannPatternFactory orbFlannPatternFactory;

    @Autowired
    CriterioDeAceptacion criterio;

    @Override
    public Producto getBestCandidate(MultipartFile multipartFile) throws Exception {

        //productoRepository.findAll();
        Mat search = Imgcodecs.imread("C:\\Users\\Usuario\\IdeaProjects\\iveo-backend\\src\\test\\resources\\bd\\coca-cola.jpg", Imgcodecs.IMREAD_GRAYSCALE);
        String url = "C:\\Users\\Usuario\\IdeaProjects\\iveo-backend\\src\\test\\resources\\bd";

        File directoryPath = new File(url);
        //List of all files and directories
        File filesList[] = directoryPath.listFiles();

        List<File> candidates = Arrays.stream(filesList).filter(file -> criterio.criterio(orbFlannPatternFactory.compare(search, Imgcodecs.imread(file.getAbsolutePath(), Imgcodecs.IMREAD_GRAYSCALE)))).collect(Collectors.toList());
        candidates.stream().forEach(file -> {
            System.out.println(file.getAbsolutePath());
        });
        return null;
    }

}

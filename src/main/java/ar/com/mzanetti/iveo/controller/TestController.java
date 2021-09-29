package ar.com.mzanetti.iveo.controller;

import ar.com.mzanetti.iveo.business.CompareBusiness;
import ar.com.mzanetti.iveo.business.MatchBusiness;
import ar.com.mzanetti.iveo.business.ProductoBusiness;
import ar.com.mzanetti.iveo.dto.ProductoDto;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    CompareBusiness compareBusiness;
    @Autowired
    MatchBusiness business;
    @Autowired
    ProductoBusiness productoBusiness;

    @RequestMapping("/new")
    public ResponseEntity<String> addProductoTest() throws Exception {
        ProductoDto producto = new ProductoDto();
        producto.setColor("rojo");
        producto.setDescripcion("Coca Cola Comun");
        producto.setEnvase("Lata");
        producto.setMaterial("Metal");
        producto.setUsuarioId("1");
        String img2Url = "C:\\Users\\Usuario\\IdeaProjects\\iveo-backend\\src\\test\\resources\\bd\\1.png";
        FileInputStream input = new FileInputStream(img2Url);
        MultipartFile multipartFile = new MockMultipartFile("fileItem",
                "1.png", "image/png", IOUtils.toByteArray(input));
        List<MultipartFile> imagenes = new ArrayList<>();
        imagenes.add(multipartFile);
        producto.setImagenes(imagenes);
        productoBusiness.procesar(producto);
        return ResponseEntity.ok("sucess");
    }

    @RequestMapping("/match")
    public ResponseEntity<String> matchProductoTest() throws Exception {
        String img2Url = "C:\\Users\\Usuario\\IdeaProjects\\iveo-backend\\src\\test\\resources\\bd\\8.png";
        FileInputStream input = new FileInputStream(img2Url);
        MultipartFile multipartFile = new MockMultipartFile("fileItem",
                "1.png", "image/png", IOUtils.toByteArray(input));
        business.getCandidate(multipartFile);
        return ResponseEntity.ok("sucess");
    }
}

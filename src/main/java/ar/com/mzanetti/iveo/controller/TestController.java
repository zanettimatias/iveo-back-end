package ar.com.mzanetti.iveo.controller;

import ar.com.mzanetti.iveo.business.CompareBusiness;
import ar.com.mzanetti.iveo.business.MatchBusiness;
import ar.com.mzanetti.iveo.business.ProductoBusiness;
import ar.com.mzanetti.iveo.dto.ProductoDto;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

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

    @RequestMapping(path = "/new/multi", method = POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> addNewMulti(@RequestParam String modelo, @RequestPart("files") List<MultipartFile> multipartFile, HttpServletRequest httpServletRequest) throws Exception {
        // request.getHeaderNames();
        return ResponseEntity.ok("sucess");
    }


    @RequestMapping(path = "/new/single", method = POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> addNewSingle(HttpServletRequest request) throws Exception {
        MultipartFile multipartFile = new MockMultipartFile("match.jsp", request.getInputStream());
        ProductoDto dto = new ProductoDto();
        dto.setUsuarioId("asdsfaf");
        dto.getImagenes().add(multipartFile);
        productoBusiness.procesar(dto);
        return ResponseEntity.ok("sucess");
    }

    @RequestMapping("/axios")
    public ResponseEntity<String> axios() throws Exception {
        System.out.println("llegue");
        return ResponseEntity.ok("sucess");
    }


}

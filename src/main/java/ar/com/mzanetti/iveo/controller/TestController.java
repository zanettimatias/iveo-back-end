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

    @RequestMapping("/match")
    public ResponseEntity<String> matchProductoTest() throws Exception {
        String img2Url = "C:\\Users\\Usuario\\IdeaProjects\\iveo-backend\\src\\test\\resources\\bd\\8.png";
        FileInputStream input = new FileInputStream(img2Url);
        MultipartFile multipartFile = new MockMultipartFile("fileItem",
                "1.png", "image/png", IOUtils.toByteArray(input));
       // business.getCandidate(multipartFile);
        return ResponseEntity.ok("sucess");
    }

    @RequestMapping(path = "/new/multi", method = POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> addNewMulti(String marca, String modelo, String material, String envase,
                                              String contenido, String color, String descripcion,
                                              @RequestPart("files") List<MultipartFile> multipartFile, HttpServletRequest request) throws Exception {
        // request.getHeaderNames();
        return ResponseEntity.ok("sucess");
    }

    @RequestMapping("/axios")
    public ResponseEntity<String> axios() throws Exception {
        System.out.println("llegue");
        return ResponseEntity.ok("sucess");
    }


}

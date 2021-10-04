package ar.com.mzanetti.iveo.controller;

import ar.com.mzanetti.iveo.business.MatchBusiness;
import ar.com.mzanetti.iveo.business.ProductoBusiness;
import ar.com.mzanetti.iveo.dto.ProductoDto;
import ar.com.mzanetti.iveo.dto.SpeakDto;
import ar.com.mzanetti.iveo.persistence.Producto;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.swing.*;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/producto/")
public class ProductoController {

    @Autowired
    ProductoBusiness productoBusiness;
    @Autowired
    MatchBusiness business;

    /***
     * Metodo para agregar un nuevo producto
     * */
    @RequestMapping(path = "/new", method = POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> addNewMulti(String marca, String modelo, String material, String envase,
                                              String contenido, String color, String descripcion,
                                              @RequestPart("files") List<MultipartFile> files, HttpServletRequest request) throws Exception {
        ProductoDto productoDto = new ProductoDto(marca,modelo,material,envase,contenido,color,descripcion, files);
        productoBusiness.procesar(productoDto);
        return ResponseEntity.ok("sucess");
    }


    @RequestMapping(path = "/match", method = POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String > match(
                                              @RequestPart("files") List<MultipartFile> files, HttpServletRequest request) throws Exception {
        Producto producto = business.getCandidate(files.get(0));
        return ResponseEntity.ok("new SpeakDto()");
    }
}

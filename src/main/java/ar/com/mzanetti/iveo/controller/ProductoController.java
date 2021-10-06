package ar.com.mzanetti.iveo.controller;

import ar.com.mzanetti.iveo.business.ImagenBusines;
import ar.com.mzanetti.iveo.business.MatchBusiness;
import ar.com.mzanetti.iveo.business.ProductoBusiness;
import ar.com.mzanetti.iveo.dto.ImageFoundDto;
import ar.com.mzanetti.iveo.dto.ProductoDto;
import ar.com.mzanetti.iveo.dto.SpeakDto;
import ar.com.mzanetti.iveo.persistence.Patrones;
import ar.com.mzanetti.iveo.persistence.Producto;
import ar.com.mzanetti.iveo.repository.ProductoRepository;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.function.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import javax.swing.*;
import java.io.FileInputStream;
import java.time.Duration;
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
    @Autowired
    ProductoRepository productoRepository;

    /***
     * Metodo para agregar un nuevo producto
     * */
    @PreAuthorize("@autorizationService.hasRole(authentication, 'ROLE_USUARIO')")
    @RequestMapping(path = "/new", method = POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> addNewMulti(String tipo, String marca, String modelo, String material, String envase,
                                              String contenido, String color, String descripcion,
                                              @RequestPart("files") List<MultipartFile> files, HttpServletRequest request) throws Exception {
        ProductoDto productoDto = new ProductoDto(tipo,marca,modelo,material,envase,contenido,color,descripcion, files);
        productoBusiness.procesar(productoDto);
        return ResponseEntity.ok("sucess");
    }


    /***
     * Metodo para matchear el producto, este metodo es reactivo, para que sea mas eficiente la busqueda
     * */
    @RequestMapping(path = "/match", method = POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Flux<SpeakDto>> match(
                                              @RequestPart("files") List<MultipartFile> files, HttpServletRequest request) throws Exception {
        Flux<SpeakDto> padrones = business.getCandidate(files.get(0)).log();
        return ResponseEntity.ok(padrones);

    }
}

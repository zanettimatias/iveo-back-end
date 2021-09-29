package ar.com.mzanetti.iveo.controller;

import ar.com.mzanetti.iveo.business.MatchBusiness;
import ar.com.mzanetti.iveo.business.ProductoBusiness;
import ar.com.mzanetti.iveo.dto.ProductoDto;
import ar.com.mzanetti.iveo.persistence.Producto;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.*;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/producto/")
public class ProductoController {

    @Autowired
    ProductoBusiness productoBusiness;
    @Autowired
    MatchBusiness business;

    /***
     * Metodo para agrega un nuevo producto
     * */
    @RequestMapping("/new")
    public ResponseEntity<String> addProducto(@RequestBody ProductoDto dto) throws Exception {
        productoBusiness.procesar(dto);
        return ResponseEntity.ok("sucess");
    }

    @RequestMapping("/match")
    public ResponseEntity<Producto> match(MultipartFile match) throws Exception {
        Producto producto = business.getCandidate(match);
        return ResponseEntity.ok(producto);
    }
}

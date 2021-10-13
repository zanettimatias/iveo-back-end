package ar.com.mzanetti.iveo.controller;

import ar.com.mzanetti.iveo.business.*;
import ar.com.mzanetti.iveo.dto.ImageFoundDto;
import ar.com.mzanetti.iveo.dto.ProductoDto;
import ar.com.mzanetti.iveo.dto.SpeakDto;
import ar.com.mzanetti.iveo.repository.ProductoRepository;
import ar.com.mzanetti.iveo.utils.OrbPatternUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
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
    @Autowired
    AprendizajeBusiness aprendizajeBusiness;
    @Autowired
    ORBFlannPatternBusiness orbFlannPatternBusiness;

    /***
     * Metodo para agregar un nuevo producto
     * */
    @PreAuthorize("@autorizationService.hasRole(authentication, 'ROLE_USUARIO')")
    @RequestMapping(path = "/new", method = POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> addNewMulti(String tipo, String marca, String modelo, String material, String envase,
                                              String contenido, String color, String descripcion,
                                              @RequestPart("files") List<MultipartFile> files,HttpServletRequest request) throws Exception {
        String user = (String) request.getAttribute("user");
        ProductoDto productoDto = new ProductoDto(user,tipo,marca,modelo,material,envase,contenido,color,descripcion, files);
        productoBusiness.procesar(productoDto);
        return ResponseEntity.ok("sucess");
    }


    /***
     * Metodo para matchear el producto, este metodo es reactivo, para que sea mas eficiente la busqueda
     *
     * @1 Busco el candidato
     * @2 Si es un mactch cercano agrego la foto, para que tenga mas informacion el proyecto
     * */
    @RequestMapping(path = "/match", method = POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Flux<SpeakDto>> match(
                                              @RequestPart("files") List<MultipartFile> files, HttpServletRequest request) throws Exception {

        BufferedImage bufferedImage = ImageIO.read(files.get(0).getInputStream());
        OrbPatternUtil match = orbFlannPatternBusiness.procesarImgBufferedORB(bufferedImage);
        Flux<ImageFoundDto> padrones;
        /* Se recupera el user, para hacer una busqueda mas intelligente*/
        String user = (String) request.getAttribute("user");
        if(user != null){
            padrones = business.getCandidateByUser(match, user);
        }else {
            padrones = business.getCandidate(match);
        }
        Flux<SpeakDto> speak = padrones.flatMap(imageFoundDto -> productoRepository.findById(imageFoundDto.getProductoId()).map(SpeakDto::new));
        return ResponseEntity.ok(speak);

    }
}

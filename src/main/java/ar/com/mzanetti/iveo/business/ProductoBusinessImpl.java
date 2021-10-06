package ar.com.mzanetti.iveo.business;

import ar.com.mzanetti.iveo.dto.ProductoDto;
import ar.com.mzanetti.iveo.dto.ProductoMatchDto;
import ar.com.mzanetti.iveo.persistence.Imagen;
import ar.com.mzanetti.iveo.persistence.Patrones;
import ar.com.mzanetti.iveo.persistence.Producto;
import ar.com.mzanetti.iveo.repository.PatronesRepository;
import ar.com.mzanetti.iveo.service.CompareInterface;
import ar.com.mzanetti.iveo.service.DtoService;
import ar.com.mzanetti.iveo.service.ProductoService;
import ar.com.mzanetti.iveo.utils.OrbPatternUtil;
import org.bson.json.JsonObject;
import org.bson.types.ObjectId;
import org.opencv.core.MatOfKeyPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.awt.*;
import java.io.IOException;
import java.util.List;

@Service
public class ProductoBusinessImpl implements ProductoBusiness {

    @Autowired
    ProductoService productoService;
    @Autowired
    DtoService dtoService;
    @Autowired
    YoloNetBusiness yoloNetBusiness;
    @Autowired
    ORBFlannPatternBusiness ORBFlannPatternBusiness;
    @Autowired
    KeyPointsBusiness keyPointsBusiness;
    @Autowired
    DescriptorsBusiness descriptorsBusiness;
    @Autowired
    PatronesRepository patronesRepository;

    @Override
    public Producto save(ProductoDto dto) throws Exception {
        return productoService.save(dtoService.producto(dto));
    }

    /**
     * @Param ProductoDto
     * Funcion para procesar el producto
     * @1 Descompone el Dto, para el producto
     * @2 Busca las clases de YOLO dentro de la imagen
     * @3 Guarda el producto en la BD
     **/
    @Override
    public Producto procesar(ProductoDto dto) throws Exception {
        this.validarProducto(dto);
        Producto producto = dtoService.producto(dto);
        producto.getImagenes().stream().forEach(this::procesarEncodedDataToPatrones);
        Producto saved = productoService.save(producto);
        saved.getImagenes().forEach(imagen -> {
            imagen.getPatrones().setProductoId(producto.getId());
            patronesRepository.save(imagen.getPatrones()).block();
        });
        return saved;
    }

    private void validarProducto(ProductoDto dto) {
    }


    private Boolean procesarEncodedDataToPatrones(Imagen imagen) {
        {
            OrbPatternUtil orbPatternUtil = null;
            try {
                orbPatternUtil = ORBFlannPatternBusiness.procesarImagenORB(imagen);
                imagen.getPatrones().setKeyPoints(keyPointsBusiness.keypointsToJson(orbPatternUtil.getKeyPoints()));
                imagen.getPatrones().setDescriptors(descriptorsBusiness.matToJson(orbPatternUtil.getDescriptors()));
                imagen.getPatrones().setYoloClases(yoloNetBusiness.getClassFounded(imagen));
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }
}

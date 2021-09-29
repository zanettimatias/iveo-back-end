package ar.com.mzanetti.iveo.business;

import ar.com.mzanetti.iveo.dto.ProductoDto;
import ar.com.mzanetti.iveo.dto.ProductoMatchDto;
import ar.com.mzanetti.iveo.persistence.Producto;
import ar.com.mzanetti.iveo.service.DtoService;
import ar.com.mzanetti.iveo.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoBusinessImpl implements ProductoBusiness {

    @Autowired
    ProductoService productoService;
    @Autowired
    DtoService dtoService;
    @Autowired
    YoloNetBusiness yoloNetBusiness;

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
        producto.getImagenes().forEach(imagen -> imagen.setPatrones(yoloNetBusiness.getClassFounded(imagen)));
        return productoService.save(producto);
    }

    private void validarProducto(ProductoDto dto) {
    }
}

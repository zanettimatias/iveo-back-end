package ar.com.mzanetti.iveo.business;

import ar.com.mzanetti.iveo.dto.ProductoDto;
import ar.com.mzanetti.iveo.persistence.Producto;
import ar.com.mzanetti.iveo.service.DtoService;
import ar.com.mzanetti.iveo.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * @1
     *
     * **/
    @Override
    public Producto procesar(ProductoDto dto) throws Exception {
        Producto producto = dtoService.producto(dto);
        producto.getImagenes().forEach(imagen -> imagen.setPatrones(yoloNetBusiness.getClassFounded(imagen)));
        return productoService.save(producto);
    }

}

package ar.com.mzanetti.iveo.business;

import ar.com.mzanetti.iveo.dto.ProductoDto;
import ar.com.mzanetti.iveo.persistence.Producto;
import ar.com.mzanetti.iveo.service.DtoService;
import ar.com.mzanetti.iveo.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public class ProductoBusinessImpl implements ProductoBusiness {

    @Autowired
    ProductoService productoService;
    @Autowired
    DtoService dtoService;

    @Override
    public Producto save(ProductoDto dto) throws Exception {
        return productoService.save(dtoService.producto(dto));
    }

}

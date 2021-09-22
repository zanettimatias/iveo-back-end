package ar.com.mzanetti.iveo.service;

import ar.com.mzanetti.iveo.persistence.Producto;
import ar.com.mzanetti.iveo.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductoServiceImpl implements ProductoService{

    @Autowired
    ProductoRepository productoRepository;

    @Override
    public Producto save(Producto producto) throws Exception {
        return productoRepository.save(producto);
    }
}

package ar.com.mzanetti.iveo.service;

import ar.com.mzanetti.iveo.persistence.Imagen;
import ar.com.mzanetti.iveo.persistence.Producto;
import ar.com.mzanetti.iveo.repository.ImagenRepository;
import ar.com.mzanetti.iveo.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    ProductoRepository productoRepository;

    @Override
    @Transactional
    public Producto save(Producto producto) throws Exception {
        return productoRepository.insert(producto);
    }
}

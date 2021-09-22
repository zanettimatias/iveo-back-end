package ar.com.mzanetti.iveo.service;

import ar.com.mzanetti.iveo.dto.ProductoDto;
import ar.com.mzanetti.iveo.persistence.Producto;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DtoServiceImpl implements DtoService {
    @Override
    public Producto producto(ProductoDto dto) throws Exception {
        Producto producto = new Producto();
        producto.setMarca(dto.getMarca());
        producto.setModelo(dto.getModelo());
        producto.setEnvase(dto.getEnvase());
        producto.setMaterial(dto.getMaterial());
        producto.setColor(dto.getColor());
        producto.setFechaCreacion(new Date());
        producto.setFechaUltMod(new Date());
        producto.setUsuarioId(dto.getUsuarioId());
        producto.setContenido(dto.getContenido());
        producto.setImage(new Binary(BsonBinarySubType.BINARY, dto.getImagen().getBytes()));
        producto.setActivo(true);
        return producto;
    }
}

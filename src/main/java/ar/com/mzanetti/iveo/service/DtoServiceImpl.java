package ar.com.mzanetti.iveo.service;

import ar.com.mzanetti.iveo.dto.ProductoDto;
import ar.com.mzanetti.iveo.persistence.Imagen;
import ar.com.mzanetti.iveo.persistence.Producto;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
        producto.setImagenes(trasnformToImagen(dto.getImagenes()));
        producto.setActivo(true);
        return producto;
    }

    private List<Imagen> trasnformToImagen(List<MultipartFile> imagenes) throws Exception {
        return imagenes.stream().map(multipartFile -> {
            try {
                return new Imagen(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
    }
}

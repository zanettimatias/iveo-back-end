package ar.com.mzanetti.iveo.dto;

import ar.com.mzanetti.iveo.persistence.Imagen;
import ar.com.mzanetti.iveo.persistence.Patrones;
import org.bson.types.Binary;
import org.bson.types.ObjectId;

import java.util.List;

public class ImageFoundDto {

    private ObjectId id;
    private Binary image;
    private Patrones patrones;
    private int match;
    private ObjectId productoId;

    public ImageFoundDto(ObjectId id, Patrones patrones, ObjectId productoId) {
        this.id = id;
        this.patrones = patrones;
        this.productoId = productoId;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Binary getImage() {
        return image;
    }

    public void setImage(Binary image) {
        this.image = image;
    }

    public Patrones getPatrones() {
        return patrones;
    }

    public void setPatrones(Patrones patrones) {
        this.patrones = patrones;
    }

    public int getMatch() {
        return match;
    }

    public void setMatch(int match) {
        this.match = match;
    }

    public ObjectId getProductoId() {
        return productoId;
    }

    public void setProductoId(ObjectId productoId) {
        this.productoId = productoId;
    }
}

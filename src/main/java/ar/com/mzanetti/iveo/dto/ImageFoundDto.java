package ar.com.mzanetti.iveo.dto;

import ar.com.mzanetti.iveo.persistence.Imagen;
import org.bson.types.Binary;
import org.bson.types.ObjectId;

import java.util.List;

public class ImageFoundDto {

    private ObjectId id;
    private Binary image;
    private List<Integer> patrones;
    private int match;

    public ImageFoundDto(Imagen imagen) {
        this.id = imagen.getId();
        this.image = imagen.getImage();
        this.patrones = imagen.getPatrones();
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

    public List<Integer> getPatrones() {
        return patrones;
    }

    public void setPatrones(List<Integer> patrones) {
        this.patrones = patrones;
    }

    public int getMatch() {
        return match;
    }

    public void setMatch(int match) {
        this.match = match;
    }
}

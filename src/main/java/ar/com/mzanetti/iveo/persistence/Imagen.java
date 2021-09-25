package ar.com.mzanetti.iveo.persistence;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Imagen {
    @Id
    private long id;
    private Binary image;
    private List<Integer> patrones;

    public Imagen(Binary image) {
        this.image = image;
    }

    public Binary getImage() {
        return image;
    }

    public void setImage(Binary image) {
        this.image = image;
    }

    public Imagen(Binary image, List<Integer> patrones) {
        this.image = image;
        this.patrones = patrones;
    }

    public List<Integer> getPatrones() {
        return patrones;
    }

    public void setPatrones(List<Integer> patrones) {
        this.patrones = patrones;
    }
}

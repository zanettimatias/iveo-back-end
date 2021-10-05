package ar.com.mzanetti.iveo.persistence;

import ar.com.mzanetti.iveo.annotations.Cascade;
import org.bson.json.JsonObject;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Imagen {
    @Id
    private ObjectId id;
    private Binary image;
    @DBRef
    @Cascade
    private Patrones patrones = new Patrones();

    public Imagen(Binary image, Patrones patrones) {
        this.image = image;
        this.patrones = patrones;
    }

    public Imagen(Binary image) {
        this.image = image;
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
}

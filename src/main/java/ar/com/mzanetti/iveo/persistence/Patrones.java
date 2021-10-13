package ar.com.mzanetti.iveo.persistence;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Patrones {
    @Id
    private ObjectId id;
    private List<Integer> yoloClases;
    private String keyPoints;
    private String descriptors;
    private ObjectId productoId;
    private String user;

    public Patrones() {
    }

    public ObjectId getProductoId() {
        return productoId;
    }

    public void setProductoId(ObjectId productoId) {
        this.productoId = productoId;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public List<Integer> getYoloClases() {
        return yoloClases;
    }

    public void setYoloClases(List<Integer> yoloClases) {
        this.yoloClases = yoloClases;
    }

    public String getKeyPoints() {
        return keyPoints;
    }

    public void setKeyPoints(String keyPoints) {
        this.keyPoints = keyPoints;
    }

    public String getDescriptors() {
        return descriptors;
    }

    public void setDescriptors(String descriptors) {
        this.descriptors = descriptors;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    public Patrones user(String user) {
        this.setUser(user);
        return this;
    }
    public Patrones productoId(ObjectId producto) {
        this.setProductoId(producto);
        return this;
    }
}

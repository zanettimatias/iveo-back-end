package ar.com.mzanetti.iveo.persistence;

import org.bson.json.JsonObject;
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
}

package ar.com.mzanetti.iveo.dto;

public class OrbPatternUtilDto {

    private String keyPoints;

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

    private String descriptors;

    public OrbPatternUtilDto(String keyPoints, String descriptors) {
        this.keyPoints = keyPoints;
        this.descriptors = descriptors;
    }

}

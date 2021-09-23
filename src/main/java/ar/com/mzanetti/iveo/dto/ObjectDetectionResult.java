package ar.com.mzanetti.iveo.dto;

public class ObjectDetectionResult {

    public ObjectDetectionResult(int classId, String className, float confidence, double x, double y, double width, double height) {
        this.classId = classId;
        this.className = className;
        this.confidence = confidence;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int classId;
    public String className;
    public float confidence;
    public double x;
    public double y;
    public double width;
    public double height;

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public float getConfidence() {
        return confidence;
    }

    public void setConfidence(float confidence) {
        this.confidence = confidence;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}

package ar.com.mzanetti.iveo.dto;

import ar.com.mzanetti.iveo.persistence.Producto;

public class SpeakDto {

    String tipo;
    String descripcion;
    String marca;
    String modelo;
    String material;
    String envase;
    String contenido;
    String color;

    public SpeakDto(Producto producto) {
        this.descripcion = producto.getDescripcion();
        this.marca = producto.getMarca();
        this.modelo = producto.getModelo();
        this.material = producto.getMaterial();
        this.envase = producto.getEnvase();
        this.contenido = producto.getContenido();
        this.color = producto.getColor();
        this.tipo =  producto.getTipo();
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getEnvase() {
        return envase;
    }

    public void setEnvase(String envase) {
        this.envase = envase;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public SpeakDto() {
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}

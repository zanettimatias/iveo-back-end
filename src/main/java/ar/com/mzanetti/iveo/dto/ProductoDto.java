package ar.com.mzanetti.iveo.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class ProductoDto {
    public String usuarioId;
    String tipo;
    String marca;
    String modelo;
    String material;
    String envase;
    String contenido;
    String color;
    String descripcion;
    List<MultipartFile> files;

    public ProductoDto(String tipo, String marca, String modelo, String material, String envase, String contenido, String color, String descripcion, List<MultipartFile> files) {
        this.tipo = tipo;
        this.marca = marca;
        this.modelo = modelo;
        this.material = material;
        this.envase = envase;
        this.contenido = contenido;
        this.color = color;
        this.descripcion = descripcion;
        this.files = files;
    }

    public ProductoDto(String usuarioId, String tipo, String marca, String modelo, String material, String envase, String contenido, String color, String descripcion, List<MultipartFile> files) {
        this.usuarioId = usuarioId;
        this.tipo = tipo;
        this.marca = marca;
        this.modelo = modelo;
        this.material = material;
        this.envase = envase;
        this.contenido = contenido;
        this.color = color;
        this.descripcion = descripcion;
        this.files = files;
    }

    public ProductoDto() {
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}

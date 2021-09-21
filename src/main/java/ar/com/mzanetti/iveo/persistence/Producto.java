package ar.com.mzanetti.iveo.persistence;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
public class Producto {
    @Id
    public long id;
    public String usuario_id;
    String marca;
    String modelo;
    String material;
    String envase;
    String contenido;
    String color;
    String descripcion;
    public Binary image;
    public Date fechaCreacion;
    public Date fechaUltMod;
    public Boolean activo;


}

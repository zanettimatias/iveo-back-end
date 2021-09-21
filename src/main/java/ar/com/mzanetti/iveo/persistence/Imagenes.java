package ar.com.mzanetti.iveo.persistence;

import org.springframework.data.annotation.Id;

public class Imagenes {

    @Id
    public String id;
    String marca;
    String modelo;
    String material;
    String envase;
    String contenido;
    String color;
    String descripcion;

}

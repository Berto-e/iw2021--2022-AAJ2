package com.example.application.classes;

import org.springframework.context.annotation.Primary;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Pelicula {
    @Id
    private int id_pelicula;
    private String nombre;
    private String actores;
    private String director;
    private Date fecha_estreno;
    private String sinopsis;
    private String genero;
    @OneToMany
    List<Proyeccion> proyecciones;

    public String getNombre() {
        return nombre;
    }

    public String getActores() {
        return actores;
    }

    public String getDirector() {
        return director;
    }

    public Date getFecha_estreno() {
        return fecha_estreno;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public String getGenero() {
        return genero;
    }

}

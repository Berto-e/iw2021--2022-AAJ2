package com.example.application.classes;

import org.springframework.context.annotation.Primary;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Pelicula {
    @Id
    private int Id;
    private String nombre;
    private String actores;
    private String director;
    private Date fecha_estreno;
    private String sinopsis;
    private String genero;
    @OneToMany
    private List<Proyeccion> proyecciones;
    @javax.persistence.Id
    private Long id;

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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

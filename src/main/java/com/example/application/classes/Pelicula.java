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
    private final String nombre;
    private final String actores;
    private final String director;
    private final Date fecha_estreno;
    private final String sinopsis;
    private final String genero;
    @OneToMany
    private List<Proyeccion> proyecciones;


    public Pelicula(String nombre, String actores, String director, Date fecha_estreno, String sinopsis, String genero, int id_pelicula) {
        this.Id = id_pelicula;
        this.nombre = nombre;
        this.actores = actores;
        this.director = director;
        this.fecha_estreno = fecha_estreno;
        this.sinopsis = sinopsis;
        this.genero = genero;
        this.proyecciones = new ArrayList<>();
    }

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

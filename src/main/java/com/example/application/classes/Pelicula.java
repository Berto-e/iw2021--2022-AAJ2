package com.example.application.classes;

import java.util.ArrayList;
import java.util.Date;

public class Pelicula {
    private final String nombre;
    private final String actores;
    private final String director;
    private final Date fecha_estreno;
    private final String sinopsis;
    private final String genero;
    private ArrayList<Proyeccion> proyecciones;

    public Pelicula(String nombre, String actores, String director, Date fecha_estreno, String sinopsis, String genero) {
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
}

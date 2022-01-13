package com.example.application.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Pelicula {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id_pelicula;
    private String url;
    private String nombre;
    private String actores;
    private String director;
    private LocalDate fecha_estreno;
    private String sinopsis;
    private String genero;
    @OneToMany(mappedBy = "pelicula")
    List<Proyeccion> proyecciones;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pel_cine")
    private Cine cine_p;

    public String getUrl() {
        return url;
    }



    public void setUrl(String url) {
        this.url = url;
    }

    public Cine getCine_p() {
        return cine_p;
    }

    public void setCine_p(Cine cine_p) {
        this.cine_p = cine_p;
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

    public LocalDate getFecha_estreno() {
        return fecha_estreno;
    }

    public String getFecha(){ return fecha_estreno.toString();}

    public String getSinopsis() {
        return sinopsis;
    }

    public String getGenero() {
        return genero;
    }

    public int getId_pelicula() {
        return id_pelicula;
    }

    public void setId_pelicula(int id_pelicula) {
        this.id_pelicula = id_pelicula;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setActores(String actores) {
        this.actores = actores;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setFecha_estreno(LocalDate fecha_estreno) {
        this.fecha_estreno = fecha_estreno;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public List<Proyeccion> getProyecciones() {
        return proyecciones;
    }

    public void setProyecciones(List<Proyeccion> proyecciones) {
        this.proyecciones = proyecciones;
    }
}

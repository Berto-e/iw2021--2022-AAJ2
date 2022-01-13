package com.example.application.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Cine {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id_cine;
    private String nombre;
    private String ubicacion;
    private boolean funcional;
    private boolean activa;


    @OneToMany(mappedBy = "cine")
    List<Sala> salas;
    @OneToMany(mappedBy = "cine_p")
    List<Pelicula> peliculas;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cine_per")
    private Persona cine_pers;


    @OneToMany(mappedBy = "cine_of")
    List<Oferta> ofertas;

    public boolean isFuncional() {
        return funcional;
    }

    public void setFuncional(boolean funcional) {
        this.funcional = funcional;
    }

    public Persona getCine_pers() {
        return cine_pers;
    }

    public void setCine_pers(Persona cine_pers) {
        this.cine_pers = cine_pers;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }


    public List<Sala> getSalas() {
        return salas;
    }

    public void setSalas(List<Sala> salas) {
        this.salas = salas;
    }

    public List<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(List<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

    public List<Oferta> getOfertas() {
        return ofertas;
    }

    public void setOfertas(List<Oferta> ofertas) {
        this.ofertas = ofertas;
    }

    public int getId_cine() {
        return id_cine;
    }

    public void setId_cine(int id_cine) {
        this.id_cine = id_cine;
    }
}

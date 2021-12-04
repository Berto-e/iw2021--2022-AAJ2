package com.example.application.classes;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cine {
    @Id
    private int id_cine;
    private String nombre;
    private String ubicacion;
    @OneToOne
    private Persona gestor_cine;
    @OneToMany
    List<Sala> salas;
    @OneToMany
    List<Pelicula> peliculas;
    @OneToMany
    List<Oferta> ofertas;


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }
}

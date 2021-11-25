package com.example.application.classes;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;

@Entity
public class Cine {
    @Id
    private int id_cine;
    private String nombre;
    private String ubicacion;
    @OneToOne private Gestor gestor_cine;
    @OneToMany private ArrayList<Pelicula> peliculas;
    @OneToMany private ArrayList<Sala> salas;
    @OneToMany private ArrayList<Oferta> ofertas;

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

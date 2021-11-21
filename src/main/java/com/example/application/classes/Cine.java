package com.example.application.classes;

import java.util.ArrayList;

public class Cine {
    private String nombre;
    private final String ubicacion;
    private Gestor gestor_cine;
    private ArrayList<Pelicula> peliculas;
    private ArrayList<Sala> salas;
    private ArrayList<Oferta> ofertas;

    public Cine(String nombre, String ubicacion, Gestor gestor_cine) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.gestor_cine = gestor_cine;
        this.peliculas = new ArrayList<>();
        this.salas = new ArrayList<>();
        this.ofertas = new ArrayList<>();
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
}

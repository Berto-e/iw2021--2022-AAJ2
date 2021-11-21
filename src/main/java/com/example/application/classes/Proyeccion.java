package com.example.application.classes;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalTime;
import java.util.ArrayList;

@Entity
public class Proyeccion {
    @Id
    private int id_proyeccion;
    private final String tipo;
    private final float precio;
    private final LocalTime hora;
    @OneToMany
    private ArrayList<Entrada> entradas;

    public Proyeccion(String tipo, float precio, LocalTime hora, int id_proyeccion) {
        this.id_proyeccion = id_proyeccion;
        this.tipo = tipo;
        this.precio = precio;
        this.hora = hora;
        this.entradas = new ArrayList<>();
    }

    public String getTipo() {
        return tipo;
    }

    public float getPrecio() {
        return precio;
    }

    public LocalTime getHora() {
        return hora;
    }
}

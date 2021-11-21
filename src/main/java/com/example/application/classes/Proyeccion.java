package com.example.application.classes;

import java.time.LocalTime;
import java.util.ArrayList;

public class Proyeccion {
    private final String tipo;
    private final float precio;
    private final LocalTime hora;
    private ArrayList<Entrada> entradas;

    public Proyeccion(String tipo, float precio, LocalTime hora) {
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

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
    private String tipo;
    private float precio;
    private LocalTime hora;


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

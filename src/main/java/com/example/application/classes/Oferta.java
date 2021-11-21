package com.example.application.classes;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Oferta {
    @Id
    private long id_Oferta;
    private int numero;
    private float precio;
    private String descripcion;

    public Oferta(int numero, float precio, String descripcion) {
        this.numero = numero;
        this.precio = precio;
        this.descripcion = descripcion;
    }

    public Oferta() {}

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}

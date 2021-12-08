package com.example.application.classes;

import javax.persistence.*;

@Entity
public class Oferta {
    @Id
    private int id_oferta;
    private int numero;
    private float precio;
    private String descripcion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ofertacine")
    private Cine cine_of;


    public Cine getCine_of() {
        return cine_of;
    }

    public void setCine_of(Cine cine_of) {
        this.cine_of = cine_of;
    }

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

    public int getId_oferta() {
        return id_oferta;
    }

    public void setId_oferta(int id_oferta) {
        this.id_oferta = id_oferta;
    }
}

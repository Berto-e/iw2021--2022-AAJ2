package com.example.application.classes;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Proyeccion {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id_proyeccion;
    private String tipo;
    private String precio;
    private LocalDateTime hora;
    @OneToMany(mappedBy = "proyeccion")
    List<Entrada> entradas;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "peliculaid")
    private Pelicula pelicula;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sala_proy")
    private Sala sala;


    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public String getTipo() {
        return tipo;
    }


    public LocalDateTime getHora() {
        return hora;
    }

    public void setHora(LocalDateTime hora) {
        this.hora = hora;
    }

    public int getId_proyeccion() {
        return id_proyeccion;
    }

    public void setId_proyeccion(int id_proyeccion) {
        this.id_proyeccion = id_proyeccion;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<Entrada> getEntradas() {
        return entradas;
    }

    public void setEntradas(List<Entrada> entradas) {
        this.entradas = entradas;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }


}

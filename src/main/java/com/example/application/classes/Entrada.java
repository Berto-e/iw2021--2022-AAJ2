package com.example.application.classes;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Date;

@Entity
public class Entrada {
    @Id
    private int id_entrada;
    private Date fecha_entrada;
    private LocalTime hora_entrada;
    private int num_sala;
    private int fila;
    private int columna;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "proy_entrada")
    private Proyeccion proyeccion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "entrada_per")
    private Persona persona_ent;

    public Proyeccion getProyeccion() {
        return proyeccion;
    }

    public void setProyeccion(Proyeccion proyeccion) {
        this.proyeccion = proyeccion;
    }

    public Persona getPersona_ent() {
        return persona_ent;
    }

    public void setPersona_ent(Persona persona_ent) {
        this.persona_ent = persona_ent;
    }

    public Date getFecha_entrada() {
        return fecha_entrada;
    }

    public void setFecha_entrada(Date fecha_entrada) {
        this.fecha_entrada = fecha_entrada;
    }

    public LocalTime getHora_entrada() {
        return hora_entrada;
    }

    public void setHora_entrada(LocalTime hora_entrada) {
        this.hora_entrada = hora_entrada;
    }

    public int getNum_sala() {
        return num_sala;
    }

    public void setNum_sala(int num_sala) {
        this.num_sala = num_sala;
    }

    public int getId_entrada() {
        return id_entrada;
    }

    public void setId_entrada(int id_entrada) {
        this.id_entrada = id_entrada;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }
}

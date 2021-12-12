package com.example.application.classes;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Sala {
    @Id
    private int id_sala;
    private int num_sala;
    private int num_asientos;
    private int num_filas;
    private String status;
    @OneToMany(mappedBy = "sala")
    List<Proyeccion> proyecciones;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sala_cine")
    private Cine cine;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Cine getCine() {
        return cine;
    }

    public void setCine(Cine cine) {
        this.cine = cine;
    }

    public int getNum_sala() {
        return num_sala;
    }

    public int getNum_asientos() {
        return num_asientos;
    }

    public int getNum_filas() {
        return num_filas;
    }

    public int getId_sala() {
        return id_sala;
    }

    public void setId_sala(int id_sala) {
        this.id_sala = id_sala;
    }

    public void setNum_sala(int num_sala) {
        this.num_sala = num_sala;
    }

    public void setNum_asientos(int num_asientos) {
        this.num_asientos = num_asientos;
    }

    public void setNum_filas(int num_filas) {
        this.num_filas = num_filas;
    }

    public List<Proyeccion> getProyecciones() {
        return proyecciones;
    }

    public void setProyecciones(List<Proyeccion> proyecciones) {
        this.proyecciones = proyecciones;
    }
}

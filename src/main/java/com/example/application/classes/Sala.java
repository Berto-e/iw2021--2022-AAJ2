package com.example.application.classes;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Sala {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id_sala;
    private String num_sala;
    private String num_asientos;
    private int num_filas;
    private String status;
    private boolean funcional;
    @OneToMany(mappedBy = "sala")
    List<Proyeccion> proyecciones;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sala_cine")
    private Cine cine;

    public void setId_sala(Integer id_sala) {
        this.id_sala = id_sala;
    }

    public boolean isFuncional() {
        return funcional;
    }

    public void setFuncional(boolean funcional) {
        this.funcional = funcional;
    }

    public Integer getId_sala() {
        return id_sala;
    }

    public void setId_sala(int id_sala) {
        this.id_sala = id_sala;
    }

    public String getNum_sala() {
        return num_sala;
    }

    public void setNum_sala(String num_sala) {
        this.num_sala = num_sala;
    }

    public String getNum_asientos() {
        return num_asientos;
    }

    public void setNum_asientos(String num_asientos) {
        this.num_asientos = num_asientos;
    }

    public int getNum_filas() {
        return num_filas;
    }

    public void setNum_filas(int num_filas) {
        this.num_filas = num_filas;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Proyeccion> getProyecciones() {
        return proyecciones;
    }

    public void setProyecciones(List<Proyeccion> proyecciones) {
        this.proyecciones = proyecciones;
    }

    public Cine getCine() {
        return cine;
    }

    public void setCine(Cine cine) {
        this.cine = cine;
    }
}

package com.example.application.classes;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalTime;
import java.util.Date;
@Entity
public class Entrada {
    @Id
    private long id_Entrada;
    private Date fecha_entrada;
    private LocalTime hora_entrada;
    private int num_sala;
    private int fila;
    private int columna;

    public Entrada(Date fecha_entrada, LocalTime hora_entrada, int num_sala, int fila, int columna) {
        this.fecha_entrada = fecha_entrada;
        this.hora_entrada = hora_entrada;
        this.num_sala = num_sala;
        this.fila = fila;
        this.columna = columna;
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

    public Entrada() {}

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


}

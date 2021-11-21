package com.example.application.classes;

import java.time.LocalTime;
import java.util.Date;

public class Entrada {
    private Date fecha_entrada;
    private LocalTime hora_entrada;
    private int num_sala;
    private int[] asiento;

    public Entrada(Date fecha_entrada, LocalTime hora_entrada, int num_sala, int[] asiento) {
        this.fecha_entrada = fecha_entrada;
        this.hora_entrada = hora_entrada;
        this.num_sala = num_sala;
        this.asiento = asiento;
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

    public int[] getAsiento() {
        return asiento;
    }

    public void setAsiento(int[] asiento) {
        this.asiento = asiento;
    }
}

package com.example.application.classes;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;

@Entity
public class Sala {
    @Id
    private int id_sala;
    private int num_sala;
    private int num_asientos;
    private int num_filas;
    @OneToMany
    private ArrayList<Proyeccion> proyecciones;

    public int getNum_sala() {
        return num_sala;
    }

    public int getNum_asientos() {
        return num_asientos;
    }

    public int getNum_filas() {
        return num_filas;
    }
}

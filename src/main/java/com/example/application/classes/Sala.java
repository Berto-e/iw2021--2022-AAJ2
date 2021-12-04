package com.example.application.classes;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Sala {
    @Id
    private int id_sala;
    private int num_sala;
    private int num_asientos;
    private int num_filas;
    @OneToMany
    List<Proyeccion> proyecciones;

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

package com.example.application.classes;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;

@Entity
public class Administrador extends Persona {
    @Id
    private int id_administrador;
    @OneToMany private ArrayList<Cine> cines;
}

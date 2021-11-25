package com.example.application.classes;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.Date;

@Entity
public class Gestor extends Persona {
    @Id
    private int id_gestor;
    @OneToOne private Cine cine;
    @OneToMany private ArrayList<Oferta> ofertas;
}

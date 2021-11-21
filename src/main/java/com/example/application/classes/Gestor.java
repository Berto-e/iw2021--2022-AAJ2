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
    private long id_Gestor;
    @OneToOne
    private Cine cine;
    @OneToMany
    private ArrayList<Oferta> ofertas;
    public Gestor(String nombre, String correo, String nom_usuario, String contrasenna, Date fecha_nacimiento, Cine cine) {
        super(nombre, correo, nom_usuario, contrasenna, fecha_nacimiento);
        this.cine = cine;
        this.ofertas = new ArrayList<>();
    }

    public Gestor() {
        super();
    }
}

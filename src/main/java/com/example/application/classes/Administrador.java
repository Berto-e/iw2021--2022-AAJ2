package com.example.application.classes;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;

@Entity
public class Administrador extends Persona {
    @Id
    private long id_admin;
    @OneToMany
    private ArrayList<Cine> cines;
    public Administrador(String nombre, String correo, String nom_usuario, String contrasenna, Date fecha_nacimiento) {
        super(nombre, correo, nom_usuario, contrasenna, fecha_nacimiento);
        cines = new ArrayList<>();
    }

    public Administrador() {
        super();
    }
}

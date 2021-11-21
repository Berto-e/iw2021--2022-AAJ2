package com.example.application.classes;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;
@Entity
public class Usuario extends Persona {
    @Id
    private long id_usuario;
    @OneToMany
    private ArrayList<Entrada> entradas;
    public Usuario(String nombre, String correo, String nom_usuario, String contrasenna, Date fecha_nacimiento) {
        super(nombre, correo, nom_usuario, contrasenna, fecha_nacimiento);
        entradas = new ArrayList<>();
    }

}

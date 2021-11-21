package com.example.application.classes;

import java.util.ArrayList;
import java.util.Date;

public class Administrador extends Persona {
    private ArrayList<Cine> cines;
    public Administrador(String nombre, String correo, String nom_usuario, String contrasenna, Date fecha_nacimiento) {
        super(nombre, correo, nom_usuario, contrasenna, fecha_nacimiento);
        cines = new ArrayList<>();
    }
}

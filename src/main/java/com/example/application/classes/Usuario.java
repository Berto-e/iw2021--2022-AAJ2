package com.example.application.classes;

import java.util.ArrayList;
import java.util.Date;

public class Usuario extends Persona {
    private ArrayList<Entrada> entradas;
    public Usuario(String nombre, String correo, String nom_usuario, String contrasenna, Date fecha_nacimiento) {
        super(nombre, correo, nom_usuario, contrasenna, fecha_nacimiento);
        entradas = new ArrayList<>();
    }

}

package com.example.application.classes;

import java.util.ArrayList;
import java.util.Date;

public class Gestor extends Persona {
    private Cine cine;
    private ArrayList<Oferta> ofertas;
    public Gestor(String nombre, String correo, String nom_usuario, String contrasenna, Date fecha_nacimiento, Cine cine) {
        super(nombre, correo, nom_usuario, contrasenna, fecha_nacimiento);
        this.cine = cine;
        this.ofertas = new ArrayList<>();
    }
}

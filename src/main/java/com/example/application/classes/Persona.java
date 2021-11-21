package com.example.application.classes;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
class Persona {
    @Id
    private int id_persona;

    private String nombre;
    private String correo;
    private String nom_usuario;
    private String contrasenna;
    private Date fecha_nacimiento;

    public Persona(String nombre, String correo, String nom_usuario, String contrasenna, Date fecha_nacimiento) {
        this.nombre = nombre;
        this.correo = correo;
        this.nom_usuario = nom_usuario;
        this.contrasenna = contrasenna;
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNom_usuario() {
        return nom_usuario;
    }

    public void setNom_usuario(String nom_usuario) {
        this.nom_usuario = nom_usuario;
    }

    public String getContrasenna() {
        return contrasenna;
    }

    public void setContrasenna(String contrasenna) {
        this.contrasenna = contrasenna;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }
}


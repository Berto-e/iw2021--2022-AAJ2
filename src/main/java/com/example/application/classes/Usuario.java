package com.example.application.classes;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;

@Entity
public class Usuario extends Persona {
    @Id
    private int id_persona;

}

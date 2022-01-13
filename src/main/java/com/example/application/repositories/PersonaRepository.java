package com.example.application.repositories;

import com.example.application.domain.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonaRepository extends JpaRepository<Persona, Integer> {

    

    List<Persona> findByFuncional(boolean b);

    Persona findByUsername(String s);

    Persona findByCorreo(String correo);
}

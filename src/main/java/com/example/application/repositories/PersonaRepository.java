package com.example.application.repositories;

import com.example.application.classes.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<Persona, Integer> {

}

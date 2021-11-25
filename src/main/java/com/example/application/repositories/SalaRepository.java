package com.example.application.repositories;

import com.example.application.classes.Sala;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SalaRepository extends JpaRepository<Sala, Integer> {

}
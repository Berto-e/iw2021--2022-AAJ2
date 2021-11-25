package com.example.application.repositories;

import com.example.application.classes.Cine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CineRepository extends JpaRepository<Cine, Integer> {

}
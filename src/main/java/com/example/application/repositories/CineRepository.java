package com.example.application.repositories;

import com.example.application.classes.Cine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CineRepository extends JpaRepository<Cine, Integer> {

    List<Cine> findByFuncional(boolean b);
}
package com.example.application.repositories;

import com.example.application.classes.Pelicula;
import com.example.application.classes.Proyeccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ProyeccionRepository extends JpaRepository<Proyeccion, Integer> {

    List<Proyeccion> findByPelicula(Pelicula peli);
}

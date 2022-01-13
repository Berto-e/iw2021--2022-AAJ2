package com.example.application.repositories;

import com.example.application.domain.Pelicula;
import com.example.application.domain.Proyeccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProyeccionRepository extends JpaRepository<Proyeccion, Integer> {

    List<Proyeccion> findByPelicula(Pelicula peli);
}

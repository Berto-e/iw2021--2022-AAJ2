package com.example.application.repositories;

import com.example.application.domain.Oferta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OfertaRepository extends JpaRepository<Oferta, Integer> {

    List<Oferta> findByActiva(boolean b);
}

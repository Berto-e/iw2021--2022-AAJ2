package com.example.application.repositories;

import com.example.application.classes.Cine;
import com.example.application.classes.Entrada;
import com.example.application.classes.Pelicula;
import com.example.application.classes.Proyeccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

import java.time.LocalDate;
import java.util.List;

@Service
public class EntradaService extends CrudService<Entrada, Integer> {

    private EntradaRepository repository;

    public EntradaService(@Autowired EntradaRepository repository) {
        this.repository = repository;
    }

    @Override
    protected EntradaRepository getRepository() {
        return repository;
    }

    public List<Entrada> findAll() { return this.repository.findAll();
    }

}
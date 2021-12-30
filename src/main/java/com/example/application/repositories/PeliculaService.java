package com.example.application.repositories;

import com.example.application.classes.Cine;
import com.example.application.classes.Oferta;
import com.example.application.classes.Pelicula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

import java.util.List;

@Service
public class PeliculaService extends CrudService<Pelicula, Integer> {

    private PeliculaRepository repository;

    public PeliculaService(@Autowired PeliculaRepository repository) {
        this.repository = repository;
    }

    @Override
    protected PeliculaRepository getRepository() {
        return repository;
    }

    public List<Pelicula> findAll() {
        return repository.findAll();
    }

}
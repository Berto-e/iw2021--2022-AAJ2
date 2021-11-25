package com.example.application.repositories;

import com.example.application.classes.Pelicula;
import com.example.application.classes.Proyeccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class ProyeccionService extends CrudService<Proyeccion, Integer> {

    private ProyeccionRepository repository;

    public ProyeccionService(@Autowired ProyeccionRepository repository) {
        this.repository = repository;
    }

    @Override
    protected ProyeccionRepository getRepository() {
        return repository;
    }
}
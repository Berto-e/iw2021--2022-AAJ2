package com.example.application.repositories;

import com.example.application.classes.Entrada;
import com.example.application.classes.Gestor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class GestorService extends CrudService<Gestor, Integer> {

    private GestorRepository repository;

    public GestorService(@Autowired GestorRepository repository) {
        this.repository = repository;
    }

    @Override
    protected GestorRepository getRepository() {
        return repository;
    }
}
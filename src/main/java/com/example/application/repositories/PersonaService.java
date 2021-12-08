package com.example.application.repositories;

import com.example.application.classes.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class PersonaService extends CrudService<Persona, Integer> {

    private PersonaRepository repository;

    @Autowired
    public PersonaService( PersonaRepository repository) {
        this.repository = repository;
    }

    @Override
    protected PersonaRepository getRepository() {
        return repository;
    }
}
package com.example.application.repositories;

import com.example.application.classes.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.artur.helpers.CrudService;

public class PersonaService extends CrudService<Persona, Integer> {

    private PersonaRepository repository;

    public PersonaService(@Autowired PersonaRepository repository) {
        this.repository = repository;
    }

    @Override
    protected PersonaRepository getRepository() {
        return repository;
    }
}
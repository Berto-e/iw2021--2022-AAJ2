package com.example.application.repositories;


import com.example.application.classes.Sala;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class SalaService extends CrudService<Sala, Integer> {

    private SalaRepository repository;

    public SalaService(@Autowired SalaRepository repository) {
        this.repository = repository;
    }

    @Override
    protected SalaRepository getRepository() {
        return repository;
    }
}
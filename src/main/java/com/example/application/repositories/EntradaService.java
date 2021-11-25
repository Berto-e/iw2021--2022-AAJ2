package com.example.application.repositories;

import com.example.application.classes.Cine;
import com.example.application.classes.Entrada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

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
}
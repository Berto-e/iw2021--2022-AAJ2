package com.example.application.services;

import com.example.application.domain.Entrada;
import com.example.application.repositories.EntradaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

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
package com.example.application.repositories;


import com.example.application.classes.Cine;
import com.example.application.classes.Sala;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

import java.util.List;

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

    public List<Sala> findByVisible(boolean b) { return this.repository.findByFuncional(b);
    }
}
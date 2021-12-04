package com.example.application.repositories;

import com.example.application.classes.Cine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class CineService extends CrudService<Cine, Integer> {

    private CineRepository repository;

    public CineService(@Autowired CineRepository repository) {
        this.repository = repository;
    }

    @Override
    protected CineRepository getRepository() {
        return repository;
    }
}
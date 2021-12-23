package com.example.application.repositories;

import com.example.application.classes.Oferta;
import com.example.application.classes.Pelicula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

import java.util.List;

@Service
public class OfertaService extends CrudService<Oferta, Integer> {

    private OfertaRepository repository;

    public OfertaService(@Autowired OfertaRepository repository) {
        this.repository = repository;
    }

    @Override
    protected OfertaRepository getRepository() {
        return repository;
    }

    public List<Oferta> findAll() {
        return repository.findAll();
    }
}

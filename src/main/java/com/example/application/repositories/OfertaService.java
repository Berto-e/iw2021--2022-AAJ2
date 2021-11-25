package com.example.application.repositories;

import com.example.application.classes.Oferta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

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
}

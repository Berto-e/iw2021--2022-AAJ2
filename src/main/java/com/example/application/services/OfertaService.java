package com.example.application.services;

import com.example.application.domain.Oferta;
import com.example.application.repositories.OfertaRepository;
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

    public List<Oferta> findByActiva(boolean b) { return this.repository.findByActiva(b);
    }
}

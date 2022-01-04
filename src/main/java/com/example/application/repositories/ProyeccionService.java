package com.example.application.repositories;

import com.example.application.classes.Pelicula;
import com.example.application.classes.Proyeccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProyeccionService extends CrudService<Proyeccion, Integer> {

    private ProyeccionRepository repository;
    public ProyeccionService(@Autowired ProyeccionRepository repository) {
        this.repository = repository;
    }

    @Override
    protected ProyeccionRepository getRepository() {
        return repository;
    }

    public boolean buscasesion(Pelicula peli, LocalDate f, int id) {
        for(Proyeccion p: this.repository.findByPelicula(peli)){
            if(p.getSala().getCine().getId_cine() == id && p.getHora().toLocalDate().equals(f) ){
                return true;
            }
        }
        return false;
    }

    public List<Proyeccion> findAll() { return repository.findAll();
    }
}
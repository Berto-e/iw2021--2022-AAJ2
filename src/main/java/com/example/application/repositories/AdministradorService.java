package com.example.application.repositories;
import com.example.application.classes.Administrador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
    public class AdministradorService extends CrudService<Administrador, Integer> {

        private AdministradorRepository repository;

        public AdministradorService(@Autowired AdministradorRepository repository) {
            this.repository = repository;
        }

        @Override
        protected AdministradorRepository getRepository() {
            return repository;
        }
    }


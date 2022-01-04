package com.example.application.repositories;

import com.example.application.classes.Persona;
import com.vaadin.flow.component.notification.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

import java.util.List;

@Service
public class PersonaService extends CrudService<Persona, Integer> implements UserDetailsService {

    private PersonaRepository repository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public PersonaService( PersonaRepository repository, PasswordEncoder passwordEncoder ) {
        super();
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected PersonaRepository getRepository() {
        return repository;
    }


    public List<Persona> findByVisible(boolean b) { return this.repository.findByFuncional(b);
    }



    public Persona loadUserByUsername(String s) throws UsernameNotFoundException, DataAccessException {
        Persona persona = repository.findByUsername(s);
        if(persona == null){
            Notification.show("Usuario no encontrado");
            throw new UsernameNotFoundException(s);
        }
        return persona;
    }

    public Persona loadUserByCorreo(String correo) {
        Persona persona = repository.findByCorreo(correo);
        if(persona == null){
            Notification.show("Email no encontrado");
            throw new UsernameNotFoundException(correo);
        }
        return persona;
    }
}
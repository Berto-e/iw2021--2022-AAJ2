package com.example.application.views.reservas;

import com.example.application.classes.Entrada;
import com.example.application.classes.Persona;
import com.example.application.repositories.*;
import com.example.application.security.SecurityUtils;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.List;

@PageTitle("Registro")
@Route(value = "Reservas", layout = MainLayout.class)
@Uses(Icon.class)
public class reservasview extends VerticalLayout {
    private EntradaService entradaService;
    private DatePicker fecha;
    private List<Entrada> entrada;
    private SecurityUtils securityUtils = new SecurityUtils();
    private SecurityService securityService;
    private Persona persona;
    private PersonaService personaService;
    private H4 mostrar;

    public reservasview(@Autowired EntradaService entradas, PersonaService personaService, SecurityService securityService) {
        this.entradaService = entradas;
        this.personaService = personaService;
        this.securityService = securityService;
        entrada = this.entradaService.findAll();
        UserDetails userLogged;
        userLogged = securityUtils.getAuthenticatedUser();
        persona = personaService.findByUsername(userLogged.getUsername());
        fecha = new DatePicker("Fecha de la sesion");
        fecha.setValue(LocalDate.now());
        fecha.addValueChangeListener(e -> {
            //Notification.show(persona.getUsername());
            //removeAll();
            //add(fecha);
            for (Entrada ent : entrada) {
                //Notification.show(ent.getPersona_ent().getUsername());
                if(ent.getPersona_ent().getUsername() == persona.getUsername()){
                    Notification.show("hola");
                }
                if(ent.getPersona_ent().getUsername().equals(persona.getUsername()) && e.getValue().getMonth().equals(ent.getFecha_entrada().getMonth())){
                    mostrar = new H4();
                    mostrar.setText("Pelicula: "+ent.getProyeccion().getPelicula().getNombre()+"\n"+"Hora: "+ent.getFecha_entrada().getHour()+" "+ent.getFecha_entrada().getMinute()+"\n"+"Sala: "+ent.getProyeccion().getSala().getNum_sala()+"\n"+"Fila: "+ent.getFila()+" Butaca: "+ent.getColumna()+"\n");
                    add(mostrar);
                }
            }
        });
        add(fecha);
    }


}

package com.example.application.views.registro;

import com.example.application.classes.Persona;
import com.example.application.repositories.PersonaService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

@Secured({"0","1","2"})
@PageTitle("PasswordRecover")
@Route(value = "verificausuario")

public class reccontraseña extends Div {
    private TextField correo = new TextField("Email");
    private Button aceptar = new Button("aceptar");
    private BeanValidationBinder<Persona> binder;
    private Persona persona;
    private PersonaService personaService;
    private Persona usuarioActivo = null;

    public reccontraseña(@Autowired PersonaService personaService) {
        this.personaService = personaService;
        addClassName("person-form-view");
        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());

        binder = new BeanValidationBinder<>(Persona.class);
        binder.forField(correo).asRequired("Introduzca su nombre de usuario").bind("correo");
        correo.setPlaceholder("Introduzca su email");
        binder.bindInstanceFields(this);

        aceptar.addClickListener(e -> {

            if (this.persona == null) {
                this.persona = new Persona();
            }
            try {
                binder.writeBean(this.persona);
            } catch (ValidationException ex) {
                ex.printStackTrace();
            }
            usuarioActivo = personaService.loadUserByCorreo(persona.getCorreo());
            UI.getCurrent().getSession().setAttribute("persona",usuarioActivo);
            clearForm();
            UI.getCurrent().navigate(cambiacontraseña.class);
        });
    }

    private void clearForm () {
        binder.setBean(new Persona());
    }

    private Component createTitle () {
        return new H3("Informacion Personal");
    }

    private Component createFormLayout () {
        FormLayout formLayout = new FormLayout();
        formLayout.add(correo);
        return formLayout;
    }

    private Component createButtonLayout () {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        aceptar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(aceptar);
        return buttonLayout;
    }
}

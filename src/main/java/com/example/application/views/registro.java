package com.example.application.views;

import com.example.application.domain.Persona;
import com.example.application.services.PersonaService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@PageTitle("Registro")
@Route(value = "registro", layout = MainLayout.class)
@Uses(Icon.class)
public class registro extends Div{
    private TextField username = new TextField("username");
    private TextField apellido = new TextField("Apellido");
    private EmailField correo = new EmailField("Correo Electrónico");
    private PasswordField password = new PasswordField("Contraseña");
    private DatePicker fecha_nacimiento = new DatePicker("Fecha de nacimiento");
    private TextField telefono = new TextField("Numero de telefono");
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");
    private BeanValidationBinder<Persona> binder;
    private Persona persona;
    private PersonaService personaService;
    private String encodedpassword = new String();
    public registro(@Autowired PersonaService personaService) {
        this.personaService = personaService;
        addClassName("person-form-view");
        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());

        binder = new BeanValidationBinder<>(Persona.class);

        binder.forField(username).asRequired("Introduzca username").bind("username");
        binder.forField(apellido).asRequired("Introduzca apellido").bind("apellido");
        binder.forField(correo).asRequired("Introduzca correo").withValidator(new EmailValidator("No es un email valido.")).bind("correo");
        binder.forField(telefono).asRequired("Introduzca telefono").bind("telefono");
        binder.forField(fecha_nacimiento).asRequired("Introduzca fecha nacimiento").bind("fecha_nacimiento");
        binder.forField(password).asRequired("Introduzca la contraseña").bind("password");


        binder.bindInstanceFields(this);
        save.setClassName("pointer");
        cancel.setClassName("pointer");
        cancel.addClickListener(e -> clearForm());
        save.addClickListener(e -> {

            try {
                if (this.persona == null) {
                    this.persona = new Persona();
                }

                //Encriptacion de la contraseña con Bcrypt
                encodedpassword = passwordEncoder.encode(password.getValue());
                //e_Encriptacion
                binder.writeBean(this.persona);
                this.persona.setPassword(encodedpassword);
                if(this.personaService.findByCorreo(this.persona.getCorreo()) != null){
                    Notification.show("Correo ya existente");
                    password.setValue("");
                }
                else{
                    personaService.update(this.persona);

                    clearForm();
                    Notification.show("Datos de persona guardao.");
                    UI.getCurrent().navigate(ImageListView.class);}
            } catch (ValidationException validationException) {
                Notification.show("Faltan campos por rellenar.");
            }
        });
    }

    private void clearForm() {
        binder.setBean(new Persona());
    }

    private Component createTitle() {
        return new H3("Informacion Personal");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        correo.setErrorMessage("Introduce correo electrónico correcto");
        fecha_nacimiento.setMax(LocalDate.now().minusYears(18));
        fecha_nacimiento.setInitialPosition(LocalDate.now().minusYears(18));
        formLayout.add(username, apellido, fecha_nacimiento, telefono, correo, password);
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save);
        buttonLayout.add(cancel);
        return buttonLayout;
    }
    }

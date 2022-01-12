package com.example.application.views.InfoPerfil;


import com.example.application.classes.Persona;
import com.example.application.repositories.PersonaService;
import com.example.application.repositories.SecurityService;
import com.example.application.security.SecurityUtils;
import com.example.application.views.MainLayout;
import com.example.application.views.imagelist.ImageListView;
import com.example.application.views.registro.reccontraseña;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@PageTitle("Registro")
@Route(value = "Perfil", layout = MainLayout.class)
@Uses(Icon.class)
public class perfilview extends Div{
    private TextField username = new TextField("username");
    private TextField apellido = new TextField("Apellido");
    private EmailField correo = new EmailField("Correo Electrónico");
    private PasswordField password = new PasswordField("Contraseña");
    private DatePicker fecha_nacimiento = new DatePicker("Fecha de nacimiento");
    private TextField telefono = new TextField("Numero de telefono");
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private Button cambio = new Button("Cambiar Contraseña");
    private Button save = new Button("Save");
    private BeanValidationBinder<Persona> binder;
    private Persona persona;
    private PersonaService personaService;
    private SecurityUtils securityUtils = new SecurityUtils();
    private SecurityService securityService;

    public perfilview(@Autowired PersonaService personaService, SecurityService securityService) {
        this.personaService = personaService;
        addClassName("person-form-view");
        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());

        UserDetails userLogged;
        userLogged = securityUtils.getAuthenticatedUser();
        persona = personaService.findByUsername(userLogged.getUsername());

        username.setValue(persona.getUsername());
        apellido.setValue(persona.getApellido());
        correo.setValue(persona.getCorreo());
        telefono.setValue(persona.getTelefono());

        binder = new BeanValidationBinder<>(Persona.class);

        binder.forField(username).asRequired("Introduzca username").bind("username");
        binder.forField(apellido).asRequired("Introduzca apellido").bind("apellido");
        binder.forField(correo).asRequired("Introduzca correo").withValidator(new EmailValidator("No es un email valido.")).bind("correo");
        binder.forField(telefono).asRequired("Introduzca telefono").bind("telefono");

        binder.bindInstanceFields(this);

        save.setClassName("pointer");
        cambio.setClassName("pointer");

        cambio.addClickListener(e -> {
            UI.getCurrent().navigate(reccontraseña.class);
        });

        save.addClickListener(e -> {

            try {
                if (this.persona == null) {
                    this.persona = new Persona();
                }
                password.setValue(persona.getPassword());
                binder.writeBean(this.persona);

                    personaService.update(this.persona);
                    clearForm();
                    Notification.show("Datos de persona Actualizados.");
                    UI.getCurrent().navigate(ImageListView.class);}
            catch (ValidationException validationException) {
                validationException.printStackTrace();
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
        formLayout.add(username, apellido, telefono, correo);
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save);
        buttonLayout.add(cambio);
        return buttonLayout;
    }
}
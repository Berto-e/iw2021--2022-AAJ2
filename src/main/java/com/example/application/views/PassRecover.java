package com.example.application.views;


import com.example.application.classes.Persona;
import com.example.application.repositories.EmailSenderService;
import com.example.application.repositories.PersonaService;
import com.example.application.views.imagelist.ImageListView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;


@PageTitle("PasswordRecover")
@Route(value = "recover")
public class PassRecover extends Div {
    private TextField username = new TextField("username");
    private TextField password = new TextField("password");
    private Button aceptar = new Button("aceptar");
    private BeanValidationBinder<Persona> binder;
    private Persona persona;
    private PersonaService personaService;
    private Persona usuarioActivo = null;
    @Autowired
    private EmailSenderService emailSenderService;
    public PassRecover(@Autowired PersonaService personaService) {
        this.personaService = personaService;
        addClassName("person-form-view");
        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());

        binder = new BeanValidationBinder<>(Persona.class);
        binder.forField(username).asRequired("Introduzca su nombre de usuario").bind("username");
        binder.forField(password).asRequired("Introduzca la ultima contraseña que recuerde").bind("password");
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
            usuarioActivo = personaService.loadUserByUsername(persona.getUsername());
            triggerMail();
            clearForm();
            Notification.show("Se ha enviado un correo con los datos de recuperación");
            UI.getCurrent().navigate(ImageListView.class);
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
            formLayout.add(username, password);
            return formLayout;
        }

        private Component createButtonLayout () {
            HorizontalLayout buttonLayout = new HorizontalLayout();
            buttonLayout.addClassName("button-layout");
            aceptar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            buttonLayout.add(aceptar);
            return buttonLayout;
        }
        public void triggerMail(){
        emailSenderService.sendSimpleEmail("email",
                "RECUPERACION DE CONTRASEÑA...",
                "PasswordRecovery");
        }
    }


package com.example.application.views.registro;


import com.example.application.classes.Persona;
import com.example.application.repositories.PersonaService;
import com.example.application.views.imagelist.ImageListView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@PageTitle("PasswordRecover")
@Route(value = "cambiacontraseña")
public class cambiacontraseña extends Div implements BeforeEnterObserver {
    private PasswordField password = new PasswordField("Password");
    private Button aceptar = new Button("aceptar");
    private BeanValidationBinder<Persona> binder;
    private Persona persona;
    private PersonaService personaService;
    private Persona usuarioActivo = null;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private String encodedpassword = new String();


    public cambiacontraseña(@Autowired PersonaService personaService) {
        this.personaService = personaService;
        if (UI.getCurrent().getSession().getAttribute("persona") != null)
            persona = (Persona) UI.getCurrent().getSession().getAttribute("persona");
        addClassName("person-form-view");
        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());

        binder = new BeanValidationBinder<>(Persona.class);
        binder.forField(password).asRequired("Introduzca la nueva contraseña").bind("password");
        password.setPlaceholder("Introduzca su nueva contraseña");
        binder.bindInstanceFields(this);

        aceptar.addClickListener(e -> {

            if (this.persona == null) {
                this.persona = new Persona();
            }
            encodedpassword = passwordEncoder.encode(password.getValue());
            password.setValue(encodedpassword);
            try {
                binder.writeBean(this.persona);
            } catch (ValidationException ex) {
                ex.printStackTrace();
            }
            personaService.update(this.persona);
            clearForm();
            UI.getCurrent().navigate(ImageListView.class);
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
        formLayout.add(password);
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        aceptar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(aceptar);
        return buttonLayout;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if(UI.getCurrent().getSession().getAttribute("persona") == null){
            event.forwardTo(reccontraseña.class);
        }
    }
}
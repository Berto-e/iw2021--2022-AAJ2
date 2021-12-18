package com.example.application.views.registro;

import com.example.application.classes.Persona;
import com.example.application.repositories.PersonaService;
import com.example.application.views.MainLayout;
import com.example.application.views.imagelist.ImageListView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@PageTitle("Registro")
@Route(value = "registro", layout = MainLayout.class)
@Uses(Icon.class)
public class registro extends Div{
    private TextField nombre = new TextField("Nombre");
    private TextField apellido = new TextField("Apellido");
    private EmailField correo = new EmailField("Correo Electrónico");
    private DatePicker fecha_nacimiento = new DatePicker("Fecha de nacimiento");
    private PhoneNumberField telefono = new PhoneNumberField("Numero de telefono");

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private Binder<Persona> binder = new Binder(Persona.class);

    public registro(PersonaService personService) {
        addClassName("person-form-view");
        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());

        binder.bindInstanceFields(this);
        clearForm();
        cancel.addClickListener(e -> clearForm());
        save.addClickListener(e -> {

            personService.update(binder.getBean());
            Notification.show(binder.getBean().getClass().getSimpleName() + " Registro realizado.");
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
        correo.setErrorMessage("Introduce correo electrónico correcto");
        formLayout.add(nombre, apellido, fecha_nacimiento, telefono, correo);
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

    private static class PhoneNumberField extends CustomField<String> {
        private ComboBox<String> countryCode = new ComboBox<>();
        private TextField number = new TextField();

        public PhoneNumberField(String label) {
            setLabel(label);
            countryCode.setWidth("120px");
            countryCode.setPlaceholder("Country");
            countryCode.setPattern("\\+\\d*");
            countryCode.setPreventInvalidInput(true);
            countryCode.setItems("+354", "+91", "+62", "+98", "+964", "+353", "+44", "+972", "+39", "+225");
            countryCode.addCustomValueSetListener(e -> countryCode.setValue(e.getDetail()));
            number.setPattern("\\d*");
            number.setPreventInvalidInput(true);
            HorizontalLayout layout = new HorizontalLayout(countryCode, number);
            layout.setFlexGrow(1.0, number);
            add(layout);
        }

        @Override
        protected String generateModelValue() {
            if (countryCode.getValue() != null && number.getValue() != null) {
                String s = countryCode.getValue() + " " + number.getValue();
                return s;
            }
            return "";
        }

        @Override
        protected void setPresentationValue(String phoneNumber) {
            String[] parts = phoneNumber != null ? phoneNumber.split(" ", 2) : new String[0];
            if (parts.length == 1) {
                countryCode.clear();
                number.setValue(parts[0]);
            } else if (parts.length == 2) {
                countryCode.setValue(parts[0]);
                number.setValue(parts[1]);
            } else {
                countryCode.clear();
                number.clear();
            }
        }
    }

}

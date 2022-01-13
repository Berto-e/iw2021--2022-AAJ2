package com.example.application.views;

import com.example.application.domain.Persona;
import com.example.application.services.PersonaService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.vaadin.artur.helpers.CrudServiceDataProvider;

@Secured("2")
@PageTitle("Gestores")
@Route(value = "gestor/:personaID?/:action?(edit)", layout = MainLayout.class)
@Uses(Icon.class)
public class gestorview extends Div implements BeforeEnterObserver {

    private final String SAMPLEPERSON_ID = "personaID";
    private final String SAMPLEPERSON_EDIT_ROUTE_TEMPLATE = "gestor/%d/edit";

    private Grid<Persona> grid = new Grid<>(Persona.class, false);

    private TextField username;
    private TextField apellido;
    private EmailField correo; //emailfield
    private TextField telefono;
    private DatePicker fecha_nacimiento = new DatePicker("Fecha nacimiento");
    private PasswordField password; //passwordfield
    private Checkbox important;
    private ComboBox<Integer> clase;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private String encodedpassword = new String();
    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private BeanValidationBinder<Persona> binder;

    private Persona persona;

    private PersonaService personaService;

    public gestorview(@Autowired PersonaService personaService) {
        this.personaService = personaService;
        addClassNames("master-detail-view", "flex", "flex-col", "h-full");
        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("username").setAutoWidth(true);
        grid.addColumn("apellido").setAutoWidth(true);
        grid.addColumn("correo").setAutoWidth(true);
        grid.addColumn("telefono").setAutoWidth(true);
        grid.addColumn("fecha_nacimiento").setAutoWidth(true);
        TemplateRenderer<Persona> importantRenderer = TemplateRenderer.<Persona>of(
                        "<iron-icon hidden='[[!item.important]]' icon='vaadin:check' style='width: var(--lumo-icon-size-s); height: var(--lumo-icon-size-s); color: var(--lumo-primary-text-color);'></iron-icon><iron-icon hidden='[[item.important]]' icon='vaadin:minus' style='width: var(--lumo-icon-size-s); height: var(--lumo-icon-size-s); color: var(--lumo-disabled-text-color);'></iron-icon>")
                .withProperty("important", Persona::isImportant);
        grid.addColumn(importantRenderer).setHeader("En Activo").setAutoWidth(true);
        grid.setDataProvider(new CrudServiceDataProvider<>(personaService));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(SAMPLEPERSON_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(gestorview.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Persona.class);


        // Bind fields. This where you'd define e.g. validation rules
        binder.forField(username).asRequired("Introduzca username").bind("username");
        binder.forField(apellido).asRequired("Introduzca apellido").bind("apellido");
        binder.forField(correo).asRequired("Introduzca correo").withValidator(new EmailValidator("No es un email valido.")).bind("correo");
        binder.forField(telefono).asRequired("Introduzca telefono").bind("telefono");
        binder.forField(fecha_nacimiento).asRequired("Introduzca fecha nacimiento").bind("fecha_nacimiento");
        binder.forField(password).asRequired("Introduzca la password").bind("password");
        binder.forField(clase).asRequired("Introduzca permiso").bind("clase");




        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.persona == null) {
                    this.persona = new Persona();
                }

                //Encriptacion de la contraseña con Bcrypt
                encodedpassword = passwordEncoder.encode(password.getValue());
                password.setValue(encodedpassword);
                //e_Encriptacion
                binder.writeBean(this.persona);
                personaService.update(this.persona);

                clearForm();
                refreshGrid();
                Notification.show("Datos de persona guardao.");
                UI.getCurrent().navigate(gestorview.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the samplePerson details.");
            }
        });

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Integer> personaid = event.getRouteParameters().getInteger(SAMPLEPERSON_ID);
        if (personaid.isPresent()) {
            Optional<Persona> personaFromBackend = personaService.get(personaid.get());
            if (personaFromBackend.isPresent()) {
                populateForm(personaFromBackend.get());
            } else {
                Notification.show(
                        String.format("The requested samplePerson was not found, ID = %d", personaid.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(gestorview.class);
            }
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("flex flex-col");
        editorLayoutDiv.setWidth("400px");

        Div editorDiv = new Div();
        editorDiv.setClassName("p-l flex-grow");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        username = new TextField("username");
        apellido = new TextField("Apellido");
        correo = new EmailField("Email");
        correo.getElement().setAttribute("name", "email");
        telefono = new TextField("Telefono");
        fecha_nacimiento.setMax(LocalDate.now().minusYears(18));
        fecha_nacimiento.setInitialPosition(LocalDate.now().minusYears(18));
        important = new Checkbox("En Activo");
        important.getStyle().set("padding-top", "var(--lumo-space-m)");
        clase = new ComboBox<Integer>("Persona");
        clase.setItems(0,1);
        password = new PasswordField("password");

        Component[] fields = new Component[]{username, apellido, password, correo, telefono, fecha_nacimiento, clase, important};

        for (Component field : fields) {
            ((HasStyle) field).addClassName("full-width");
        }
        formLayout.add(fields);
        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("w-full flex-wrap bg-contrast-5 py-s px-l");
        buttonLayout.setSpacing(true);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setId("grid-wrapper");
        wrapper.setWidthFull();
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Persona value) {
        this.persona = value;
        binder.readBean(this.persona);

    }
}

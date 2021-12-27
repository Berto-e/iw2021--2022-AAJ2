package com.example.application.views.gestores;

import com.example.application.classes.Persona;
import com.example.application.repositories.PersonaService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.artur.helpers.CrudServiceDataProvider;


@PageTitle("Gestores")
@Route(value = "gestor/:personaID?/:action?(edit)", layout = MainLayout.class)
@Uses(Icon.class)
public class gestorview extends Div implements BeforeEnterObserver {

    private final String SAMPLEPERSON_ID = "personaID";
    private final String SAMPLEPERSON_EDIT_ROUTE_TEMPLATE = "gestor/%d/edit";

    private Grid<Persona> grid = new Grid<>(Persona.class, false);

    private TextField nombre;
    private TextField apellido;
    private TextField correo; //emailfield
    private TextField telefono;
    private DatePicker fecha_nacimiento;
    private TextField contrasenna; //passwordfield
    private Checkbox important;


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
        grid.addColumn("nombre").setAutoWidth(true);
        grid.addColumn("apellido").setAutoWidth(true);
        grid.addColumn("correo").setAutoWidth(true);
        grid.addColumn("telefono").setAutoWidth(true);
        grid.addColumn("fecha_nacimiento").setAutoWidth(true);
        TemplateRenderer<Persona> importantRenderer = TemplateRenderer.<Persona>of(
                        "<iron-icon hidden='[[!item.important]]' icon='vaadin:check' style='width: var(--lumo-icon-size-s); height: var(--lumo-icon-size-s); color: var(--lumo-primary-text-color);'></iron-icon><iron-icon hidden='[[item.important]]' icon='vaadin:minus' style='width: var(--lumo-icon-size-s); height: var(--lumo-icon-size-s); color: var(--lumo-disabled-text-color);'></iron-icon>")
                .withProperty("important", Persona::isImportant);
        grid.addColumn(importantRenderer).setHeader("Important").setAutoWidth(true);
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
        binder.forField(nombre).asRequired("No se puede quedar vacio").bind("nombre");
        binder.forField(nombre).asRequired("No se puede quedar vacio").bind("nombre");
        binder.forField(nombre).asRequired("No se puede quedar vacio").bind("nombre");
        binder.forField(nombre).asRequired("No se puede quedar vacio").bind("nombre");
        binder.forField(nombre).asRequired("No se puede quedar vacio").bind("nombre");
        binder.forField(nombre).asRequired("No se puede quedar vacio").bind("nombre");

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
        nombre = new TextField("Nombre");
        apellido = new TextField("Apellido");
        correo = new TextField("Email");
        telefono = new TextField("Telefono");
        fecha_nacimiento = new DatePicker("Fecha nacimiento");
        important = new Checkbox("Gestor");
        important.getStyle().set("padding-top", "var(--lumo-space-m)");

        Component[] fields = new Component[]{nombre, apellido, correo, telefono, fecha_nacimiento, important};

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

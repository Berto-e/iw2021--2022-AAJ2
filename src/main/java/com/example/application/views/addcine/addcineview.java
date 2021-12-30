package com.example.application.views.addcine;

import com.example.application.classes.Cine;
import com.example.application.classes.Oferta;
import com.example.application.classes.Persona;
import com.example.application.repositories.CineService;
import com.example.application.repositories.OfertaService;
import com.example.application.repositories.PersonaService;
import com.example.application.views.MainLayout;
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


@PageTitle("Cines")
@Route(value = "addcine/:cineID?/:action?(edit)", layout = MainLayout.class)
@Uses(Icon.class)
public class addcineview extends Div implements BeforeEnterObserver {

    private final String SAMPLEPERSON_ID = "cineID";
    private final String SAMPLEPERSON_EDIT_ROUTE_TEMPLATE = "addcine/%d/edit";

    private Grid<Cine> grid = new Grid<>(Cine.class, false);

    private TextField nombre;
    private TextField ubicacion;
    private Checkbox funcional;
    private ComboBox<Persona> cine_pers;


    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private BeanValidationBinder<Cine> binder;

    private Cine cine;

    private CineService cineService;
    private PersonaService personaService;

    public addcineview(@Autowired CineService cineService, PersonaService personaService) {
        this.cineService = cineService;
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
        grid.addColumn("ubicacion").setAutoWidth(true);

        grid.setDataProvider(new CrudServiceDataProvider<>(cineService));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(SAMPLEPERSON_EDIT_ROUTE_TEMPLATE, event.getValue().getId_cine()));
            } else {
                clearForm();
                UI.getCurrent().navigate(com.example.application.views.addoferta.addofertaview.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Cine.class);

        // Bind fields. This where you'd define e.g. validation rules
        binder.forField(nombre).asRequired("Debe introducir un nombre").bind("nombre");
        binder.forField(ubicacion).asRequired("Introduzca una ubicacion").bind("ubicacion");
        binder.forField(cine_pers).asRequired("Seleccione un administrador").bind("cine_pers");

        binder.bindInstanceFields(this);


        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.cine == null) {
                    this.cine = new Cine();
                }
                binder.writeBean(this.cine);

                cineService.update(this.cine);
                clearForm();
                refreshGrid();
                Notification.show("Datos de persona guardao.");
                UI.getCurrent().navigate(addcineview.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the samplePerson details.");
            }
        });

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Integer> cineid = event.getRouteParameters().getInteger(SAMPLEPERSON_ID);
        if (cineid.isPresent()) {
            Optional<Cine> cineidFromBackend = cineService.get(cineid.get());
            if (cineidFromBackend.isPresent()) {
                populateForm(cineidFromBackend.get());
            } else {
                Notification.show(
                        String.format("The requested oferta was not found, ID = %d", cineid.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(com.example.application.views.addoferta.addofertaview.class);
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
        ubicacion = new TextField("Ubicacion");
        funcional = new Checkbox("Activa");
        funcional.getStyle().set("padding-top", "var(--lumo-space-m)");
        cine_pers = new ComboBox<Persona>("Persona");
        cine_pers.setItems(this.personaService.findByVisible(true));
        cine_pers.setItemLabelGenerator(Persona::getNombre);
        Component[] fields = new Component[]{nombre, ubicacion, funcional, cine_pers};

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

    private void populateForm(Cine value) {
        this.cine = value;
        binder.readBean(this.cine);

    }
}


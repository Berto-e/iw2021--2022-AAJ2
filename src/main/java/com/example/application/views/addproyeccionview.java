package com.example.application.views;

import com.example.application.domain.Pelicula;
import com.example.application.domain.Proyeccion;
import com.example.application.domain.Sala;
import com.example.application.services.CineService;
import com.example.application.services.PeliculaService;
import com.example.application.services.ProyeccionService;
import com.example.application.services.SalaService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
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
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.vaadin.artur.helpers.CrudServiceDataProvider;

@Secured({"1","2"})
@PageTitle("Añadir Pelicula")
@Route(value = "Proyeccion/:proyeccionID?/:action?(edit)", layout = MainLayout.class)
@Uses(Icon.class)
public class addproyeccionview extends Div implements BeforeEnterObserver {

    private final String SAMPLEPERSON_ID = "proyeccionID";
    private final String SAMPLEPERSON_EDIT_ROUTE_TEMPLATE = "Proyeccion/%d/edit";

    private Grid<Proyeccion> grid = new Grid<>(Proyeccion.class, false);

    private TextField precio;
    private ComboBox<Sala> sala;
    private DateTimePicker hora;
    private ComboBox<Pelicula> pelicula;
    private List<Pelicula> pelis;

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private BeanValidationBinder<Proyeccion> binder;

    private Proyeccion proyeccion;
    private ProyeccionService proyeccionService;
    private SalaService salaService;
    private PeliculaService peliculaService;
    private CineService cineService;

    public addproyeccionview(@Autowired PeliculaService peliculaService, SalaService salaService, ProyeccionService proyeccionService, CineService cineService) {
        this.salaService = salaService;
        this.peliculaService = peliculaService;
        this.proyeccionService = proyeccionService;
        this.cineService = cineService;
        this.pelis = peliculaService.findAll();

        addClassNames("master-detail-view", "flex", "flex-col", "h-full");
        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("hora").setAutoWidth(true);
        grid.addColumn("precio").setAutoWidth(true);

        grid.setDataProvider(new CrudServiceDataProvider<>(proyeccionService));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(SAMPLEPERSON_EDIT_ROUTE_TEMPLATE, event.getValue().getId_proyeccion()));
            } else {
                clearForm();
                UI.getCurrent().navigate(addproyeccionview.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Proyeccion.class);

        // Bind fields. This where you'd define e.g. validation rules

        binder.forField(hora).asRequired("Introduzca el titulo de la obra").bind("hora");
        binder.forField(precio).asRequired("Introduzca a los actores").bind("precio");
        binder.forField(pelicula).asRequired("Introduzca el director de la pelicula").bind("pelicula");
        binder.forField(sala).asRequired("Introduzca la fecha de estreno de la pelicula").bind("sala");

        binder.bindInstanceFields(this);

        save.setClassName("pointer");
        cancel.setClassName("pointer");
        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.proyeccion == null) {
                    this.proyeccion = new Proyeccion();
                }
                binder.writeBean(this.proyeccion);

                proyeccionService.update(this.proyeccion);
                clearForm();
                refreshGrid();
                Notification.show("Datos de la proyeccion guardado.");
                UI.getCurrent().navigate(addproyeccionview.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the samplePerson details.");
            }
        });

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Integer> proyeccionid = event.getRouteParameters().getInteger(SAMPLEPERSON_ID);
        if (proyeccionid.isPresent()) {
            Optional<Proyeccion> proyeccionFromBackend = proyeccionService.get(proyeccionid.get());
            if (proyeccionFromBackend.isPresent()) {
                populateForm(proyeccionFromBackend.get());
            } else {
                Notification.show(
                        String.format("The requested samplePerson was not found, ID = %d", proyeccionid.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(addproyeccionview.class);
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
        hora = new DateTimePicker("Hora");
        precio = new TextField("Precio");
        sala = new ComboBox<Sala>("Sala");
        sala.setItems(this.salaService.findByVisible(true));
        sala.setItemLabelGenerator(Sala::getidstring);
        pelicula = new ComboBox<Pelicula>("Pelicula");
        pelicula.setItems(pelis);
        pelicula.setItemLabelGenerator(Pelicula::getNombre);


        Component[] fields = new Component[]{hora, precio, sala, pelicula};

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

    private void populateForm(Proyeccion value) {
        this.proyeccion = value;
        binder.readBean(this.proyeccion);
    }
}


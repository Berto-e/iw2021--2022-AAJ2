package com.example.application.views.addpeli;

import com.example.application.classes.Pelicula;
import com.example.application.classes.Sala;
import com.example.application.repositories.PeliculaService;
import com.example.application.views.MainLayout;
import com.example.application.views.salalist.salaList;
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

import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@PageTitle("Añadir Pelicula")
@Route(value = "Pelicula/:PersonaID?/:action?(edit)", layout = MainLayout.class)
@Uses(Icon.class)
public class addpeliview extends Div implements BeforeEnterObserver {

    private final String SAMPLEPERSON_ID = "samplePersonID";
    private final String SAMPLEPERSON_EDIT_ROUTE_TEMPLATE = "Pelicula/%d/edit";

    private Grid<Pelicula> grid = new Grid<>(Pelicula.class, false);

    private TextField nombre;
    private TextField actores;
    private TextField director;
    private DatePicker fecha_estreno;
    private TextField sinopsis;
    private TextField genero;
    private TextField url;

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");
    private Button borrar = new Button("Borrar");

    private BeanValidationBinder<Pelicula> binder;

    private Pelicula pelicula;

    private PeliculaService peliculaService;

    public addpeliview(@Autowired PeliculaService peliculaService) {
        this.peliculaService = peliculaService;
        addClassNames("master-detail-view", "flex", "flex-col", "h-full");
        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("nombre").setAutoWidth(true);
        grid.addColumn("actores").setAutoWidth(true);
        grid.addColumn("director").setAutoWidth(true);
        grid.addColumn("fecha_estreno").setAutoWidth(true);
        grid.addColumn("sinopsis").setAutoWidth(true);
        grid.addColumn("genero").setAutoWidth(true);

        grid.setDataProvider(new CrudServiceDataProvider<>(peliculaService));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(SAMPLEPERSON_EDIT_ROUTE_TEMPLATE, event.getValue().getId_pelicula()));
            } else {
                clearForm();
                UI.getCurrent().navigate(addpeliview.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Pelicula.class);

        // Bind fields. This where you'd define e.g. validation rules

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.pelicula == null) {
                    this.pelicula = new Pelicula();
                }
                binder.writeBean(this.pelicula);

                peliculaService.update(this.pelicula);
                clearForm();
                refreshGrid();
                Notification.show("Datos de pelicula guardao.");
                UI.getCurrent().navigate(addpeliview.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the samplePerson details.");
            }
        });

        borrar.addClickListener(e -> {
            try {
                if (this.pelicula == null) {
                    this.pelicula = new Pelicula();
                }
                binder.writeBean(this.pelicula);

                peliculaService.delete(1);
                clearForm();
                refreshGrid();
                Notification.show("Datos de peli borrado.");
                UI.getCurrent().navigate(addpeliview.class);
            }catch (ValidationException ex) {
                ex.printStackTrace();
            }
        });
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Integer> peliculaid = event.getRouteParameters().getInteger(SAMPLEPERSON_ID);
        if (peliculaid.isPresent()) {
            Optional<Pelicula> peliculaFromBackend = peliculaService.get(peliculaid.get());
            if (peliculaFromBackend.isPresent()) {
                populateForm(peliculaFromBackend.get());
            } else {
                Notification.show(
                        String.format("The requested samplePerson was not found, ID = %d", peliculaid.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(addpeliview.class);
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
        url = new TextField("URL");
        actores = new TextField("Actores");
        director = new TextField("Director");
        sinopsis = new TextField("Sinopsis");
        fecha_estreno = new DatePicker("Fecha estreno");
        genero = new TextField("Genero");
        Component[] fields = new Component[]{nombre, actores, director, sinopsis, fecha_estreno, genero, url};

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
        borrar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel,borrar);
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

    private void populateForm(Pelicula value) {
        this.pelicula = value;
        binder.readBean(this.pelicula);

    }
}

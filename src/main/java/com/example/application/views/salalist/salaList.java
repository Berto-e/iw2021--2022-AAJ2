package com.example.application.views.salalist;

import com.example.application.classes.Cine;
import com.example.application.classes.Sala;
import com.example.application.repositories.CineService;
import com.example.application.repositories.SalaService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
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

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.artur.helpers.CrudServiceDataProvider;

@PageTitle("Salas")
@Route(value = "sala/:salaID?/:action?(edit)", layout = MainLayout.class)
@Uses(Icon.class)
public class salaList extends Div implements BeforeEnterObserver {

    private final String SAMPLEPERSON_ID = "salaID";
    private final String SAMPLEPERSON_EDIT_ROUTE_TEMPLATE = "sala/%d/edit";
    private Grid<Sala> grid = new Grid<>(Sala.class, false);

    private TextField num_sala;
    private TextField num_asientos;
    private TextField status;
    private Checkbox funcional;
    private ComboBox<Cine> cine;


    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private BeanValidationBinder<Sala> binder;

    private Sala sala;

    private SalaService salaService;
    private CineService cineService;

    public salaList(@Autowired SalaService salaService, CineService cineService) {
        this.salaService = salaService;
        this.cineService = cineService;
        addClassNames("master-detail-view", "flex", "flex-col", "h-full");
        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("num_sala").setAutoWidth(true);
        grid.addColumn("num_asientos").setAutoWidth(true);
        grid.addColumn("status").setAutoWidth(true);

        grid.setDataProvider(new CrudServiceDataProvider<>(salaService));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(SAMPLEPERSON_EDIT_ROUTE_TEMPLATE, event.getValue().getId_sala()));
            } else {
                clearForm();
                UI.getCurrent().navigate(salaList.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Sala.class);

        // Bind fields. This where you'd define e.g. validation rules
        binder.forField(num_sala).asRequired("Debe introducir un numero de sala").bind("num_sala");
        binder.forField(num_asientos).asRequired("Introduzca un numero de asiento").bind("num_asientos");
        binder.forField(status).asRequired("Introduzca un estado de la sala").bind("status");
        binder.forField(cine).asRequired("Selecciona un cine").bind("cine");

        binder.bindInstanceFields(this);

        save.setClassName("pointer");
        cancel.setClassName("pointer");
        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.sala == null) {
                    this.sala = new Sala();
                }
                binder.writeBean(this.sala);
                salaService.update(this.sala);
                clearForm();
                refreshGrid();
                Notification.show("Datos guardados");
                UI.getCurrent().navigate(salaList.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the samplePerson details.");
            }
        });
    }


    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Integer> salaid = event.getRouteParameters().getInteger(SAMPLEPERSON_ID);
        if (salaid.isPresent()) {
            Optional<Sala> salaFromBackend = salaService.get(salaid.get());
            if (salaFromBackend.isPresent()) {
                populateForm(salaFromBackend.get());
            } else {
                Notification.show(
                        String.format("The requested samplePerson was not found, ID = %d", salaid.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(salaList.class);
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
        num_sala = new TextField("Numero de sala");
        num_asientos = new TextField("Asientos");
        status = new TextField("Status");
        funcional = new Checkbox("Important");
        funcional.getStyle().set("padding-top", "var(--lumo-space-m)");
        cine = new ComboBox<Cine>("Cine");
        cine.setItems(this.cineService.findByVisible(true));
        cine.setItemLabelGenerator(Cine::getNombre);
    //quitar sesion: igual que setatribute, ponemos la variable la asignamos a null
        Component[] fields = new Component[]{num_sala, num_asientos, status, funcional, cine};

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

    private void populateForm(Sala value) {
        this.sala = value;
        binder.readBean(this.sala);

    }
}

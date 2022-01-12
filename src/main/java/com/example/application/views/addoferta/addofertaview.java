package com.example.application.views.addoferta;

import com.example.application.classes.Oferta;
import com.example.application.repositories.OfertaService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
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
import org.springframework.security.access.annotation.Secured;
import org.vaadin.artur.helpers.CrudServiceDataProvider;

@Secured({"1","2"})
@PageTitle("Ofertas")
@Route(value = "addoferta/:ofertaID?/:action?(edit)", layout = MainLayout.class)
@Uses(Icon.class)
public class addofertaview extends Div implements BeforeEnterObserver {

    private final String SAMPLEPERSON_ID = "ofertaID";
    private final String SAMPLEPERSON_EDIT_ROUTE_TEMPLATE = "addoferta/%d/edit";

    private Grid<Oferta> grid = new Grid<>(Oferta.class, false);

    private TextField numero;
    private TextField precio;
    private TextField descripcion;
    private Checkbox activa;


    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private BeanValidationBinder<Oferta> binder;

    private Oferta oferta;

    private OfertaService ofertaService;

    public addofertaview(@Autowired OfertaService ofertaService) {
        this.ofertaService = ofertaService;
        addClassNames("master-detail-view", "flex", "flex-col", "h-full");
        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("numero").setAutoWidth(true);
        grid.addColumn("precio").setAutoWidth(true);
        grid.addColumn("descripcion").setAutoWidth(true);
        TemplateRenderer<Oferta> importantRenderer = TemplateRenderer.<Oferta>of(
                        "<iron-icon hidden='[[!item.activa]]' icon='vaadin:check' style='width: var(--lumo-icon-size-s); height: var(--lumo-icon-size-s); color: var(--lumo-primary-text-color);'></iron-icon><iron-icon hidden='[[item.activa]]' icon='vaadin:minus' style='width: var(--lumo-icon-size-s); height: var(--lumo-icon-size-s); color: var(--lumo-disabled-text-color);'></iron-icon>")
                .withProperty("activa", Oferta::isActiva);
        grid.addColumn(importantRenderer).setHeader("Activa").setAutoWidth(true);
        grid.setDataProvider(new CrudServiceDataProvider<>(ofertaService));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(SAMPLEPERSON_EDIT_ROUTE_TEMPLATE, event.getValue().getId_oferta()));
            } else {
                clearForm();
                UI.getCurrent().navigate(com.example.application.views.addoferta.addofertaview.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Oferta.class);

        // Bind fields. This where you'd define e.g. validation rules

        binder.bindInstanceFields(this);

        save.setClassName("pointer");
        cancel.setClassName("pointer");

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.oferta == null) {
                    this.oferta = new Oferta();
                }
                binder.writeBean(this.oferta);

                ofertaService.update(this.oferta);
                clearForm();
                refreshGrid();
                Notification.show("Datos de persona guardao.");
                UI.getCurrent().navigate(addofertaview.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the samplePerson details.");
            }
        });

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Integer> ofertaid = event.getRouteParameters().getInteger(SAMPLEPERSON_ID);
        if (ofertaid.isPresent()) {
            Optional<Oferta> ofertaFromBackend = ofertaService.get(ofertaid.get());
            if (ofertaFromBackend.isPresent()) {
                populateForm(ofertaFromBackend.get());
            } else {
                Notification.show(
                        String.format("The requested oferta was not found, ID = %d", ofertaid.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(addofertaview.class);
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
        numero = new TextField("Numero");
        precio = new TextField("Precio");
        descripcion = new TextField("Descripcion");
        activa = new Checkbox("Activa");
        activa.getStyle().set("padding-top", "var(--lumo-space-m)");

        Component[] fields = new Component[]{numero, precio, descripcion, activa};

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

    private void populateForm(Oferta value) {
        this.oferta = value;
        binder.readBean(this.oferta);

    }
}
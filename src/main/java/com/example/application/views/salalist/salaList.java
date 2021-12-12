package com.example.application.views.salalist;

import com.example.application.classes.Sala;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.gridpro.GridPro;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.data.renderer.NumberRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;

@PageTitle("List")
@Route(value = "lista-salas", layout = MainLayout.class)
public class salaList extends Div {

    private GridPro<Sala> grid;
    private ListDataProvider<Sala> dataProvider;

    private Grid.Column<Sala> idColumn;
    private Grid.Column<Sala> SalaColumn;
    private Grid.Column<Sala> statusColumn;

    public salaList() {
        addClassName("list-view");
        setSizeFull();
        createGrid();
        add(grid);
    }

    private void createGrid() {
        createGridComponent();
        addColumnsToGrid();
        addFiltersToGrid();
    }

    private void createGridComponent() {
        grid = new GridPro<>();
        grid.setSelectionMode(SelectionMode.MULTI);
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_COLUMN_BORDERS);
        grid.setHeight("100%");

        dataProvider = new ListDataProvider<>(getSalas());
        grid.setDataProvider(dataProvider);
    }

    private void addColumnsToGrid() {
        createIdColumn();
        createSalaColumn();
        createStatusColumn();
    }

    private void createIdColumn() {
        idColumn = grid.addColumn(Sala::getId_sala, "id").setHeader("ID").setWidth("120px").setFlexGrow(0);
    }

    private void createSalaColumn() {
        SalaColumn = grid.addColumn(new ComponentRenderer<>(Sala -> {
            HorizontalLayout hl = new HorizontalLayout();
            hl.setAlignItems(Alignment.CENTER);
            Span span = new Span();
            span.setClassName("name");
            span.setText(Integer.toString(Sala.getNum_sala()));
            hl.add(span);
            return hl;
        })).setComparator(Sala -> Sala.getNum_sala()).setHeader("Sala");
    }


    private void createStatusColumn() {
        statusColumn = grid.addEditColumn(Sala::getNum_sala, new ComponentRenderer<>(Sala -> {
                    Span span = new Span();
                    span.setText(Sala.getStatus());
                    span.getElement().setAttribute("theme", "badge " + Sala.getStatus().toLowerCase());
                    return span;
                })).select((item, newValue) -> item.setStatus(newValue), Arrays.asList("Pending", "Success", "Error"))
                .setComparator(Sala -> Sala.getStatus()).setHeader("Status");
    }
/*
    private void createDateColumn() {
        dateColumn = grid
                .addColumn(new LocalDateRenderer<>(Sala -> LocalDate.parse(Sala.getDate()),
                        DateTimeFormatter.ofPattern("M/d/yyyy")))
                .setComparator(Sala -> Sala.getDate()).setHeader("Date").setWidth("180px").setFlexGrow(0);
    }
*/
    private void addFiltersToGrid() {
        HeaderRow filterRow = grid.appendHeaderRow();

        TextField idFilter = new TextField();
        idFilter.setPlaceholder("Filter");
        idFilter.setClearButtonVisible(true);
        idFilter.setWidth("100%");
        idFilter.setValueChangeMode(ValueChangeMode.EAGER);
        idFilter.addValueChangeListener(event -> dataProvider.addFilter(
                Sala -> StringUtils.containsIgnoreCase(Integer.toString(Sala.getId_sala()), idFilter.getValue())));
        filterRow.getCell(idColumn).setComponent(idFilter);

        TextField SalaFilter = new TextField();
        SalaFilter.setPlaceholder("Filter");
        SalaFilter.setClearButtonVisible(true);
        SalaFilter.setWidth("100%");
        SalaFilter.setValueChangeMode(ValueChangeMode.EAGER);
        SalaFilter.addValueChangeListener(event -> dataProvider
                .addFilter(Sala -> StringUtils.containsIgnoreCase(Integer.toString(Sala.getNum_sala()), SalaFilter.getValue())));
        filterRow.getCell(SalaColumn).setComponent(SalaFilter);


        ComboBox<String> statusFilter = new ComboBox<>();
        statusFilter.setItems(Arrays.asList("Pending", "Success", "Error"));
        statusFilter.setPlaceholder("Filter");
        statusFilter.setClearButtonVisible(true);
        statusFilter.setWidth("100%");
        statusFilter.addValueChangeListener(
                event -> dataProvider.addFilter(Sala -> areStatusesEqual(Sala, statusFilter)));
        filterRow.getCell(statusColumn).setComponent(statusFilter);

    }

    private boolean areStatusesEqual(Sala Sala, ComboBox<String> statusFilter) {
        String statusFilterValue = statusFilter.getValue();
        if (statusFilterValue != null) {
            return StringUtils.equals(Sala.getStatus(), statusFilterValue);
        }
        return true;
    }

    private List<Sala> getSalas() {
        return Arrays.asList(
                createSala(4957, 15, 40, "Funcionando"),
                createSala(4937, 10, 35, "Funcionando"),
                createSala(4900, 11, 46, "Funcionando")
                );
    }

    private Sala createSala(int id, int num_filas, int num_asientos, String status) {
        Sala c = new Sala();
        c.setId_sala(id);
        c.setStatus(status);
        c.setNum_asientos(num_asientos);
        c.setNum_filas(num_filas);
        c.setCine(null);
        return c;
    }
};


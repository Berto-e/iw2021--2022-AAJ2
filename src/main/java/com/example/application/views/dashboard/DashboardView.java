package com.example.application.views.dashboard;

import com.example.application.classes.*;
import com.example.application.repositories.*;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

@Secured("0")
@PageTitle("Dashboard")
@Route(value = "dashboard", layout = MainLayout.class)
public class DashboardView extends Main {

    private CineService cineService;
    private EntradaService entradaService;
    private PeliculaService peliculaService;
    private ProyeccionService proyeccionService;
    private OfertaService ofertaService;
    private PersonaService personaService;
    private List<Cine> cineList;
    private List<Entrada> entradaList;
    private List<Pelicula> peliculaList;
    private List<Proyeccion> proyeccionList;
    private List<Oferta> ofertaList;
    private List<Persona> personaList;

    public DashboardView(@Autowired CineService cineService, EntradaService entradaService, PeliculaService peliculaService,
                         ProyeccionService proyeccionService, OfertaService ofertaService, PersonaService personaService) {
        addClassName("dashboard-view");
        this.cineService = cineService;
        this.entradaService = entradaService;
        this.peliculaService = peliculaService;
        this.proyeccionService = proyeccionService;
        this.ofertaService = ofertaService;
        this.personaService = personaService;
        this.cineList = cineService.findAll();
        this.entradaList = entradaService.findAll();
        this.peliculaList = peliculaService.findAll();
        this.proyeccionList = proyeccionService.findAll();
        this.personaList = personaService.findAll();
        this.ofertaList = ofertaService.findAll();

        Board board = new Board();
        board.addRow(createHighlight("Cines en activo", "" + cineList.size(), 33.7),
                createHighlight("Cantidad de entradas vendidas", "" + entradaList.size(), -112.45),
                createHighlight("Peliculas en cartelera", "" + peliculaList.size(), 3.9));
        //board.addRow(createResponseTimes());
        board.addRow(createHighlight("Proyecciones realizadas", "" + proyeccionList.size(), 0.0),
                createHighlight("Ofertas realizadas", "" + ofertaList.size(), 0.0),
                createHighlight("Usuarios registrados", "" + personaList.size(), 0.0));
        add(board);
    }

    private Component createHighlight(String title, String value, Double percentage) {
        VaadinIcon icon = VaadinIcon.ARROW_UP;
        String prefix = "";
        String theme = "badge";

        if (percentage == 0) {
            prefix = "±";
        } else if (percentage > 0) {
            prefix = "+";
            theme += " success";
        } else if (percentage < 0) {
            icon = VaadinIcon.ARROW_DOWN;
            theme += " error";
        }

        H2 h2 = new H2(title);
        h2.addClassNames("font-normal", "m-0", "text-secondary", "text-xs");

        Span span = new Span(value);
        span.addClassNames("font-semibold", "text-3xl");

        Icon i = icon.create();
        i.addClassNames("box-border", "p-xs");

        Span badge = new Span(i, new Span(prefix + percentage.toString()));
        badge.getElement().getThemeList().add(theme);

        VerticalLayout layout = new VerticalLayout(h2, span, badge);
        layout.addClassName("p-l");
        layout.setPadding(false);
        layout.setSpacing(false);
        return layout;
    }
/*
    private Component createViewEvents() {
        // Header
        Select year = new Select();
        year.setItems("2021", "2022");
        year.setValue("2022");
        year.setWidth("100px");

        HorizontalLayout header = createHeader("Venta de entradas", "Entradas al mes por cine");
        header.add(year);

        // Chart
        Chart chart = new Chart(ChartType.AREA);
        Configuration conf = chart.getConfiguration();

        XAxis xAxis = new XAxis();
        xAxis.setCategories("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
        conf.addxAxis(xAxis);

        conf.getyAxis().setTitle("Entradas vendidas");

        PlotOptionsArea plotOptions = new PlotOptionsArea();
        plotOptions.setPointPlacement(PointPlacement.ON);
        conf.addPlotOptions(plotOptions);

        conf.addSeries(new ListSeries("Bahia Mar", 189, 191, 191, 196, 201, 203, 209, 212, 229, 242, 244, 247));
        conf.addSeries(new ListSeries("Cines Yelmos", 138, 146, 148, 148, 152, 153, 163, 173, 178, 179, 185, 187));
        conf.addSeries(new ListSeries("Las Salinas", 65, 65, 66, 71, 93, 102, 108, 117, 127, 129, 135, 136));
        conf.addSeries(new ListSeries("Lagoh", 0, 11, 17, 23, 30, 42, 48, 49, 52, 54, 58, 62));

        // Add it all together
        VerticalLayout viewEvents = new VerticalLayout(header, chart);
        viewEvents.addClassName("p-l");
        viewEvents.setPadding(false);
        viewEvents.setSpacing(false);
        viewEvents.getElement().getThemeList().add("spacing-l");
        return viewEvents;
    }
*/
    private Component createServiceHealth() {
        // Header
        HorizontalLayout header = createHeader("Datos por cine", "Salas / Entradas");

        // Grid
        Grid<ServiceHealth> grid = new Grid();
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setAllRowsVisible(true);

        grid.addColumn(ServiceHealth::getCity).setHeader("Cine").setFlexGrow(1);
        grid.addColumn(ServiceHealth::getInput).setHeader("Salas").setAutoWidth(true).setTextAlign(ColumnTextAlign.END);
        grid.addColumn(ServiceHealth::getOutput).setHeader("Peliculas").setAutoWidth(true)
                .setTextAlign(ColumnTextAlign.END);

        grid.setItems(new ServiceHealth(cineList.get(0).getNombre(), cineList.get(0).getSalas().size(), cineList.get(0).getPeliculas().size()));

        // Add it all together
        VerticalLayout serviceHealth = new VerticalLayout(header, grid);
        serviceHealth.addClassName("p-l");
        serviceHealth.setPadding(false);
        serviceHealth.setSpacing(false);
        serviceHealth.getElement().getThemeList().add("spacing-l");
        return serviceHealth;
    }

    private Component createResponseTimes() {
        HorizontalLayout header = createHeader("Response times", "Average across all systems");

        // Chart
        Chart chart = new Chart(ChartType.PIE);
        Configuration conf = chart.getConfiguration();

        DataSeries series = new DataSeries();
        series.add(new DataSeriesItem(cineList.get(0).getNombre(), cineList.get(0).getPeliculas().size()));


        conf.addSeries(series);

        // Add it all together
        VerticalLayout serviceHealth = new VerticalLayout(header, chart);
        serviceHealth.addClassName("p-l");
        serviceHealth.setPadding(false);
        serviceHealth.setSpacing(false);
        serviceHealth.getElement().getThemeList().add("spacing-l");
        return serviceHealth;
    }

    private HorizontalLayout createHeader(String title, String subtitle) {
        H2 h2 = new H2(title);
        h2.addClassNames("text-xl", "m-0");

        Span span = new Span(subtitle);
        span.addClassNames("text-secondary", "text-xs");

        VerticalLayout column = new VerticalLayout(h2, span);
        column.setPadding(false);
        column.setSpacing(false);

        HorizontalLayout header = new HorizontalLayout(column);
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        header.setSpacing(false);
        header.setWidthFull();
        return header;
    }

}

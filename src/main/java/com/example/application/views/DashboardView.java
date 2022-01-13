package com.example.application.views;

import com.example.application.domain.*;
import com.example.application.services.*;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

@Secured("2")
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
        board.addRow(createHighlight("Cines en activo", "" + cineList.size(), 0.0),
                createHighlight("Cantidad de entradas vendidas", "" + entradaList.size(), 0.0),
                createHighlight("Peliculas en cartelera", "" + peliculaList.size(), 0.0));
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

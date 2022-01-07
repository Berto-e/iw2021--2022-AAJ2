package com.example.application.views.infoPeli;

import com.example.application.classes.Pelicula;
import com.example.application.classes.Persona;
import com.example.application.classes.Proyeccion;
import com.example.application.repositories.PeliculaService;
import com.example.application.repositories.ProyeccionService;
import com.example.application.views.MainLayout;
import com.example.application.views.cogesilla.cogesillaview;
import com.example.application.views.gestores.gestorview;
import com.example.application.views.imagelist.ImageListView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@PageTitle("About")
@Route(value = "InfoPeli/:peliculaID?", layout = MainLayout.class)
public class infopeli extends VerticalLayout implements BeforeEnterObserver {
    private PeliculaService peliculaService;
    private ProyeccionService proyeccionService;
    private List<Pelicula> pelis;
    private List<Proyeccion> proyecciones;
    private Pelicula p;
    private String nombresesion; //recojo la variable de sesion
    private final String pelicula_ID = "peliculaID";
    private H2 h = new H2("hola");
    private Paragraph p2 = new Paragraph("adios");
    Image img = new Image();
    Button asiento = new Button("Ver asientos");
    Button sesiones = new Button("Ver sesiones");
    Button sala;
    private int filas = 12;

    public infopeli(@Autowired PeliculaService peliculas, ProyeccionService proyeccionService) {
        if(UI.getCurrent().getSession().getAttribute("nombre") != null){
            nombresesion = (String) UI.getCurrent().getSession().getAttribute("nombre");
        }
        this.peliculaService = peliculas;
        this.proyeccionService = proyeccionService;
        proyecciones = this.proyeccionService.findAll();
        pelis = peliculaService.findAll();
        setSpacing(false);

        img.setWidth("150px");
        add(img);

        add(h);
        add(p2);

        sesiones.addClickListener(e -> {
            for (Proyeccion pr : proyecciones) {
                if(pr.getPelicula().getNombre().equals(this.p.getNombre())){
                    sala = new Button(""+pr.getHora().getHour()+":"+pr.getHora().getMinute());
                    sala.addClickListener(e2 -> {
                        UI.getCurrent().getSession().setAttribute("numfila",pr.getSala().getNum_filas());
                        UI.getCurrent().getSession().setAttribute("idsala",pr.getSala().getId_sala());
                        UI.getCurrent().getSession().setAttribute("horapeli",pr.getHora());
                        UI.getCurrent().navigate(cogesillaview.class);
                    });
                    add(sala);
                }
            }
        });

        add(sesiones);
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.START);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");


    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if(UI.getCurrent().getSession().getAttribute("nombre") == null){
            event.forwardTo(ImageListView.class);
        }else {
            Optional<Integer> peliid = event.getRouteParameters().getInteger(pelicula_ID);
            if (peliid.isPresent()) {
                Optional<Pelicula> personaFromBackend = peliculaService.get(peliid.get());
                if (personaFromBackend.isPresent()) {
                    populateForm(personaFromBackend.get());
                } else {
                    Notification.show(
                            String.format("The requested samplePerson was not found, ID = %d", peliid.get()), 3000,
                            Notification.Position.BOTTOM_START);
                    event.forwardTo(ImageListView.class);
                }
            }
        }
    }

    private void populateForm(Pelicula value) {
        this.p = value;
        img.setSrc(this.p.getUrl());
        h.setText("Titulo: " + this.p.getNombre());
        p2.setText("Descripción: " + this.p.getSinopsis());
    }
}

package com.example.application.views.infoPeli;

import com.example.application.classes.Pelicula;
import com.example.application.classes.Persona;
import com.example.application.repositories.PeliculaService;
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
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@PageTitle("About")
@Route(value = "InfoPeli/:peliculaID?", layout = MainLayout.class)
public class infopeli extends VerticalLayout implements BeforeEnterObserver {
    private PeliculaService peliculaService;
    private List<Pelicula> pelis;
    private Pelicula p;
    //private String nom = UI.getCurrent();
    private final String nombresesion = null; //recojo la variable de sesion

    private final String pelicula_ID = "peliculaID";
    private H2 h = new H2("hola");
    private Paragraph p2 = new Paragraph("adios");
    Image img = new Image();
    Button asiento = new Button("Ver asientos");
    private int filas = 12;
    public infopeli(@Autowired PeliculaService peliculas) {
        this.peliculaService = peliculas;
        pelis = peliculaService.findAll();
        setSpacing(false);

        img.setWidth("150px");
        add(img);

        add(h);
        add(p2);
        asiento.addClickListener(e -> {

            UI.getCurrent().getSession().setAttribute("filas", filas);
            UI.getCurrent().navigate(cogesillaview.class);
        });
        add(asiento);
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

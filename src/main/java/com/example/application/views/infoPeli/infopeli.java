package com.example.application.views.infoPeli;

import com.example.application.classes.Pelicula;
import com.example.application.repositories.PeliculaService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@PageTitle("About")
@Route(value = "InfoPeli", layout = MainLayout.class)
public class infopeli extends VerticalLayout {
    private PeliculaService personaService;
    private List<Pelicula> pelis;
    private Pelicula p;
    public infopeli(@Autowired PeliculaService peliculas) {
        this.personaService = peliculas;
        pelis = personaService.findAll();
        p = pelis.get(0);
        setSpacing(false);

        Image img = new Image("images/empty-plant.png", "hello");
        img.setWidth("150px");
        add(img);

        add(new H2(p.getNombre()));
        add(new Paragraph(p.getSinopsis()));

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.START);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }
}

package com.example.application.views.imagelist;

import com.example.application.classes.Pelicula;
import com.example.application.repositories.PeliculaRepository;
import com.example.application.repositories.PeliculaService;
import com.example.application.repositories.PersonaService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.example.application.views.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Cartelera")
@Route(value = "", layout = MainLayout.class)
@Tag("image-list-view")
@JsModule("./views/imagelist/image-list-view.ts")

public class ImageListView extends LitTemplate implements HasComponents, HasStyle {
    private PeliculaService personaService;
    public ImageListView(@Autowired PeliculaService peliculas) {
        addClassNames("image-list-view", "flex", "flex-col", "h-full");
        this.personaService = peliculas;

        for(Pelicula p: )
        add(new ImageCard("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/viernes-13-saga-peor-a-mejor-ranking-1588849712.jpeg?crop=1.00xw:0.708xh;0,0&resize=640:*", "Viernes 13 RETURNS",
                "Descripcion de la pelicula", "Terror");

    }
}
package com.example.application.views.imagelist;

import com.example.application.classes.Pelicula;
import com.example.application.classes.Persona;
import com.example.application.repositories.PeliculaService;
import com.example.application.views.MainLayout;
import com.example.application.views.gestores.gestorview;
import com.example.application.views.infoPeli.infopeli;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@PageTitle("Ofertas")
@Route(value = "", layout = MainLayout.class)
@Tag("image-list-view")
@JsModule("./views/imagelist/image-list-view.ts")

public class ImageListView extends LitTemplate implements HasComponents, HasStyle {
    private PeliculaService personaService;
    private List<Pelicula> pelis;
    private Button ver_info = new Button("Ver información");
    public ImageListView(@Autowired PeliculaService peliculas) {
        addClassNames("image-list-view", "flex", "flex-col", "h-full");
        this.personaService = peliculas;
        pelis = personaService.findAll();

        for(Pelicula p: pelis){
            add(new ImageCard(p.getUrl(), p.getNombre(),
                p.getSinopsis(), p.getGenero(),p.getId_pelicula()));

        }
    }
}

//QUE EN CARTELERA SELECCIONE PELICULAS POR CINE O HORAS.
package com.example.application.views.ofertas;

import com.example.application.classes.Oferta;
import com.example.application.classes.Pelicula;
import com.example.application.repositories.OfertaService;
import com.example.application.repositories.PeliculaService;
import com.example.application.views.MainLayout;
import com.example.application.views.imagelist.ImageCard;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@PageTitle("Cartelera")
@Route(value = "ofertas", layout = MainLayout.class)
@Tag("image-list-view")
@JsModule("./views/imagelist/image-list-view.ts")

public class ofertasview extends LitTemplate implements HasComponents, HasStyle {
    private OfertaService ofertaService;
    private List<Oferta> ofertitas;
    public ofertasview(@Autowired OfertaService ofertas) {
        addClassNames("image-list-view", "flex", "flex-col", "h-full");
        this.ofertaService = ofertas;
        ofertitas = ofertaService.findAll();
        for(Oferta o: ofertitas)
            if(o.isActiva())
            add(new ofertacard("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/viernes-13-saga-peor-a-mejor-ranking-1588849712.jpeg?crop=1.00xw:0.708xh;0,0&resize=640:*", o.getNumero(),
                    o.getDescripcion(), o.getPrecio()));
    }
}
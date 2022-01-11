
package com.example.application.views.ofertas;

import com.example.application.classes.Cine;
import com.example.application.classes.Oferta;
import com.example.application.classes.Pelicula;
import com.example.application.repositories.OfertaService;
import com.example.application.repositories.PeliculaService;
import com.example.application.views.MainLayout;
import com.example.application.views.compra.compraview;
import com.example.application.views.imagelist.ImageCard;
import com.example.application.views.infoPeli.infopeli;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
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
    private Button seguir = new Button("Seguir comprando");
    private OfertaService ofertaService;
    private List<Oferta> ofertitas;
    private ComboBox<Oferta> oferta;
    public ofertasview(@Autowired OfertaService ofertas) {
        addClassNames("image-list-view", "flex", "flex-col", "h-full");
        this.ofertaService = ofertas;
        ofertitas = ofertaService.findAll();

        oferta = new ComboBox<Oferta>("Oferta");
        oferta.setItems(this.ofertaService.findByActiva(true));
        oferta.setItemLabelGenerator(Oferta::getNumero);
        for(Oferta o: ofertitas){
            if(o.isActiva())
            add(new ofertacard(o.getUrl(), o.getNumero(),
                    o.getDescripcion(), o.getPrecio()));}

        seguir.addClickListener(e -> {
            UI.getCurrent().getSession().setAttribute("ofertita",oferta.getValue());
            UI.getCurrent().navigate(compraview.class);
        });
        add(oferta);
        add(seguir);

    }
}

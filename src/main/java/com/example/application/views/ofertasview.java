
package com.example.application.views;

import com.example.application.domain.Oferta;
import com.example.application.services.OfertaService;
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
import org.springframework.security.access.annotation.Secured;

import java.util.List;

@Secured({"0","1","2"})
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

        oferta.setClassName("pointer");
        seguir.setClassName("pointer");

        seguir.addClickListener(e -> {
            UI.getCurrent().getSession().setAttribute("ofertita",oferta.getValue());
            UI.getCurrent().navigate(compraview.class);
        });
        add(oferta);
        add(seguir);

    }
}

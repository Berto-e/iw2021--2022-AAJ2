package com.example.application.views.imagelist;

import com.example.application.views.infoPeli.infopeli;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.router.RouteParameters;

@JsModule("./views/imagelist/image-card.ts")
@Tag("image-card")
public class ImageCard extends LitTemplate {

    @Id
    private Image image;

    @Id
    private Span header;

    @Id
    private Paragraph text;

    @Id
    private Span badge;

    public ImageCard(String photo, String title, String descrip, String label, Integer id) {
        this.image.setSrc(photo);
        this.image.addClickListener(e -> {
            UI.getCurrent().getSession().setAttribute("nombre", header);//en header pones null, se acabo la sesion
            UI.getCurrent().navigate(infopeli.class,new RouteParameters("peliculaID", id.toString())); //este es para el beforeenter
        });
        this.header.setText(title);
        this.text.setText(descrip);
        this.badge.setText(label);
    }
}

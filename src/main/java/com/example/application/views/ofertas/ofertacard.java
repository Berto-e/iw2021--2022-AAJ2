package com.example.application.views.ofertas;


import com.example.application.views.compra.compraview;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.template.Id;

@JsModule("./views/imagelist/image-card.ts")
@Tag("image-card")
public class ofertacard extends LitTemplate {

    @Id
    private Image image;

    @Id
    private Span header;

    @Id
    private Paragraph text;

    @Id
    private Span badge;

    public ofertacard(String photo, String title, String descrip, String label) {
        this.image.setSrc(photo);
        this.header.setText(title);
        this.text.setText(descrip);
        this.badge.setText(""+String.valueOf(label)+"€");
    }
}
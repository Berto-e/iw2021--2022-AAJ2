package com.example.application.views.cogesilla;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Escoger asiento")
@Route(value = "asiento", layout = MainLayout.class)
@Uses(Icon.class)
public class cogesillaview extends VerticalLayout {
    public cogesillaview() {
        Image img = new Image("/images/silla-de-cine.png", "sillon");
        Image img2 = new Image("/images/silla-de-cine.png", "sillon");
        Image img3 = new Image("/images/silla-de-cine.png", "sillon");
        HorizontalLayout h = new HorizontalLayout(img, img2, img3);
        h.setSpacing(false);
        h.setWidthFull();
        h.setJustifyContentMode(JustifyContentMode.CENTER);
        add(h);
    }
}

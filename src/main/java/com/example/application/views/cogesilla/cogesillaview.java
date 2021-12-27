package com.example.application.views.cogesilla;

import com.example.application.views.MainLayout;
import com.example.application.views.addpeli.addpeliview;
import com.vaadin.flow.component.UI;
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
        for (int i = 0; i < 10; i++) {
            HorizontalLayout h = new HorizontalLayout();
            for (int j = 0; j < 10; j++){
                Image img = new Image("/images/silla-de-cine.png", "sillon");
                img.addClickListener(e ->{
                   UI.getCurrent().navigate(addpeliview.class);
                });
                h.setSpacing(false);
                h.setWidthFull();
                h.setJustifyContentMode(JustifyContentMode.CENTER);
                h.add(img);
                add(h);
            }
        }
    }
}
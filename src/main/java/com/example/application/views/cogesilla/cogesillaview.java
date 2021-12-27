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
                img.setTitle(//guardamos i);
                img.setText(//guardamos j);
                img.setAlt(//numero de cliccada);
                img.addClickListener(e -> {
                    //img.setAlt(//numero de cliccada);
                    //cont_total++
                    //cont_asientos++
                    //img.setSrc();
                    String fotoantigua = img.getSrc();
                    //compara las sources y hace lo que sea
                    //if(compara y ya estaba seleccionado)
                        //cambia al original y cont_asientos--
                        //pasa por sesion poniendo la img.setatribute("img.getalt()",null)
                    //por sesion se pasa la i y la j// la imagen// o probar con una clase como cardbox, num_asiento y num clicks
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
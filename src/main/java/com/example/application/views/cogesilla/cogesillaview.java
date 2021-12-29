package com.example.application.views.cogesilla;

import com.example.application.classes.Pelicula;
import com.example.application.views.MainLayout;
import com.example.application.views.addpeli.addpeliview;
import com.example.application.views.compra.compraview;
import com.example.application.views.imagelist.ImageListView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Optional;

@PageTitle("Escoger asiento")
@Route(value = "asiento", layout = MainLayout.class)
@Uses(Icon.class)


public class cogesillaview extends VerticalLayout implements BeforeEnterObserver {
    private int num_fila;
    private int num_clicks = 0;
    private int cont_asientos = 0;
    private int num_elegida = 0;
    private Button enviar = new Button("Seguir comprando");
    public cogesillaview() {
        if (UI.getCurrent().getSession().getAttribute("filas") != null)
            num_fila = (int) UI.getCurrent().getSession().getAttribute("filas");

            for (int i = 0; i < num_fila; i++) {
                HorizontalLayout h = new HorizontalLayout();
                for (int j = 0; j < 10; j++) {
                    Image img = new Image("/images/silla-de-cine.png", String.valueOf(num_elegida));
                    img.setTitle(String.valueOf(i));
                    img.setText(String.valueOf(j));
                    img.addClickListener(e -> {
                        num_elegida++;
                        num_clicks++;
                        cont_asientos++;
                        img.setAlt(String.valueOf(num_elegida));
                        String actual = img.getSrc();
                        if (actual == "/images/silla-de-cine.png") {
                            img.setSrc("/images/silla-de-cine-clicada.png");
                        }

                        if (actual == "/images/silla-de-cine-clicada.png") {
                            img.setSrc("/images/silla-de-cine.png");
                            cont_asientos--;
                            num_elegida--;
                            img.setAlt("0");
                            }

                        });
                    enviar.addClickListener(e -> {
                        UI.getCurrent().getSession().setAttribute("clicks", num_clicks);
                        UI.getCurrent().getSession().setAttribute("num_asientos", cont_asientos);
                        UI.getCurrent().getSession().setAttribute("filcol",img);
                        UI.getCurrent().navigate(compraview.class);
                    });

                    h.setSpacing(false);
                    h.setWidthFull();
                    h.setJustifyContentMode(JustifyContentMode.CENTER);
                    h.add(img);
                    add(h,enviar);
                }
            }
        }


    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if(UI.getCurrent().getSession().getAttribute("filas") == null){
            event.forwardTo(ImageListView.class);
        }
    }
}
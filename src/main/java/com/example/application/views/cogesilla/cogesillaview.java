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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@PageTitle("Escoger asiento")
@Route(value = "asiento", layout = MainLayout.class)
@Uses(Icon.class)


public class cogesillaview extends VerticalLayout implements BeforeEnterObserver {
    private int num_fila;
    private int num_clicks = 0;
    private int cont_asientos = -1;
    private int num_elegida = 0;
    private Button enviar = new Button("Seguir comprando");
    private int fil = 0;
    private int col = 0;
    private int escogida = 0;
    private List<Integer> l = new ArrayList<Integer>();
    public cogesillaview() {
        if (UI.getCurrent().getSession().getAttribute("numfila") != null)
            num_fila = (int) UI.getCurrent().getSession().getAttribute("numfila");

            for (int i = 0; i < num_fila; i++) {
                HorizontalLayout h = new HorizontalLayout();
                for (int j = 0; j < 10; j++) {
                    Image img = new Image("/images/silla-de-cine.png", String.valueOf(num_elegida));
                    img.setTitle(String.valueOf(i));
                    img.setText(String.valueOf(j));
                    img.setClassName(String.valueOf(i));
                    img.addClickListener(e -> {
                        num_clicks++;
                        String actual = img.getSrc();
                        if (actual == "/images/silla-de-cine.png") {
                            img.setSrc("/images/silla-de-cine-clicada.png");
                            num_elegida++;
                            img.setMaxHeight(String.valueOf(num_elegida));
                            col = Integer.valueOf(img.getText());
                            fil = Integer.valueOf(img.getClassName());
                            cont_asientos++;
                            l.add(cont_asientos,col);//meto y quito en base a contadores
                            cont_asientos++;
                            l.add(cont_asientos,fil);
                        }

                        if (actual == "/images/silla-de-cine-clicada.png") {
                            img.setSrc("/images/silla-de-cine.png");
                            escogida = Integer.valueOf(img.getMaxHeight());
                            l.remove(escogida * 2 - 2);
                            l.remove(escogida * 2 - 2);
                            cont_asientos -= 2;
                            img.setAlt("0");
                            }

                        });
                    enviar.addClickListener(e -> {
                        UI.getCurrent().getSession().setAttribute("clicks", num_clicks);
                        UI.getCurrent().getSession().setAttribute("num_asientos", cont_asientos);
                        UI.getCurrent().getSession().setAttribute("cont_asientos", cont_asientos);
                        UI.getCurrent().getSession().setAttribute("colu",col);
                        UI.getCurrent().getSession().setAttribute("fila",fil);
                        UI.getCurrent().getSession().setAttribute("lista",l);

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
        if(UI.getCurrent().getSession().getAttribute("numfila") == null){
            event.forwardTo(ImageListView.class);
        }
    }
}
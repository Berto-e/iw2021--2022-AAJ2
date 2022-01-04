package com.example.application.views.compra;

import com.example.application.classes.Pelicula;
import com.example.application.repositories.PeliculaService;
import com.example.application.views.MainLayout;
import com.example.application.views.cogesilla.cogesillaview;
import com.example.application.views.imagelist.ImageListView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import java.util.List;
import java.util.Optional;
@Secured({"0","1","2"})
@PageTitle("About")
@Route(value = "Compra", layout = MainLayout.class)
public class compraview extends VerticalLayout implements BeforeEnterObserver {

    //private String nom = UI.getCurrent();
    private final String nombresesion = null; //recojo la variable de sesion

    private H2 h = new H2("hola");
    private H2 h2 = new H2("no");
    private int numero_click;
    private int numasientos;
    private Paragraph p2 = new Paragraph("adios");
    Image img2 = new Image();
    public compraview() {

        setSpacing(false);

        add(h);
        add(p2);
        add(h2);
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.START);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if(UI.getCurrent().getSession().getAttribute("filcol") == null)
            event.forwardTo(ImageListView.class);
        if(UI.getCurrent().getSession().getAttribute("clicks") == null)
            event.forwardTo(ImageListView.class);
        if(UI.getCurrent().getSession().getAttribute("num_asientos") == null)
            event.forwardTo(ImageListView.class);

        img2 = (Image)UI.getCurrent().getSession().getAttribute("filcol");
        rellenar();
    }

    private void rellenar() {

        h.setText("Fila = " + img2.getTitle());
        p2.setText("Columna: " + img2.getText());
        h2.setText("Valor del alter: " + img2.getAlt());
    }
}


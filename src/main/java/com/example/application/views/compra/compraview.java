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

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
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
    private H2 h3 = new H2("hola");
    private H2 h4 = new H2("no");
    private int numero_click;
    private int numasientos;
    private int asientos;
    private Paragraph p2 = new Paragraph("adios");
    private int col;
    private int fil;
    private int col2;
    private int fil2;
    private List<Integer> l2 = new ArrayList<Integer>();

    public compraview() {
        if(UI.getCurrent().getSession().getAttribute("cont_asientos") != null)
            asientos = (int) UI.getCurrent().getSession().getAttribute("cont_asientos");
        if(UI.getCurrent().getSession().getAttribute("colu") != null)
            col = (int)UI.getCurrent().getSession().getAttribute("colu");
        if(UI.getCurrent().getSession().getAttribute("fila") != null)
            fil = (int)UI.getCurrent().getSession().getAttribute("fila");
        if(UI.getCurrent().getSession().getAttribute("lista") != null)
            l2 = (List<Integer>) UI.getCurrent().getSession().getAttribute("lista");

        p2.setText("Asientos = " + asientos);
        h.setText("Fila: "+fil);
        h2.setText("Columna: "+l2);
        Notification.show(""+l2);

        setSpacing(false);

        add(h);
        add(p2);
        add(h2);
        add(h3);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.START);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if(UI.getCurrent().getSession().getAttribute("clicks") == null)
            event.forwardTo(ImageListView.class);
        if(UI.getCurrent().getSession().getAttribute("num_asientos") == null)
            event.forwardTo(ImageListView.class);
        if(UI.getCurrent().getSession().getAttribute("lista") == null)
            event.forwardTo(ImageListView.class);

        asientos = (int)UI.getCurrent().getSession().getAttribute("cont_asientos");
        col = (int)UI.getCurrent().getSession().getAttribute("colu");
        fil = (int)UI.getCurrent().getSession().getAttribute("fila");
        col2 = (int)UI.getCurrent().getSession().getAttribute("colu");
        fil2 = (int)UI.getCurrent().getSession().getAttribute("fila");

    }

}


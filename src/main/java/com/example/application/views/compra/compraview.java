package com.example.application.views.compra;

import com.example.application.classes.*;
import com.example.application.repositories.*;
import com.example.application.security.SecurityUtils;
import com.example.application.views.MainLayout;
import com.example.application.views.imagelist.ImageListView;
import com.example.application.views.ofertas.ofertasview;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UserDetails;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Secured({"0","1","2"})
@PageTitle("About")
@Route(value = "Compra", layout = MainLayout.class)
public class compraview extends VerticalLayout implements BeforeEnterObserver {

    //private String nom = UI.getCurrent();
    private final String nombresesion = null; //recojo la variable de sesion

    private H2 h;
    private H2 h2 = new H2("no");
    private H4 h3;
    private H2 h4;
    private H4 nomb;
    private H2 dueño;
    private int numero_click;
    private int numasientos;
    private int asientos;
    private int columna;
    private int fila;
    private int butacones;
    private int col2;
    private int fil2;
    private LocalDateTime fecha_entrada;
    private int proid;
    private List<Proyeccion> proyecciones;
    private List<Integer> l2 = new ArrayList<Integer>();
    private EntradaService entradaService;
    private PersonaService personaService;
    private ProyeccionService proyeccionService;
    private SecurityUtils securityUtils = new SecurityUtils();
    private final SecurityService securityService;
    private String nombrepersona = "";
    private String ofertaname;
    private Proyeccion proyeccion;
    private String butacas = "";
    private Persona persona;
    private List<String> compradas = new ArrayList<String>();
    private Entrada entrada;
    private Button save = new Button("Save");
    private Oferta ofertaele = new Oferta();
    private double precio = 0.0;
    private int j = 0;
    private String posicion;
    @Autowired
    private EmailSenderService emailSenderService;


    public compraview(@Autowired EntradaService entradaService, ProyeccionService proyeccionService, SecurityService securityService, PersonaService personaService) {
        this.entradaService = entradaService;
        this.proyeccionService = proyeccionService;
        this.securityService = securityService;
        this.personaService = personaService;
        Properties properties = new Properties();
        properties.put("mail.smtp.ssl.trust", "*");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        //properties.put();
        //properties.put();
        //properties.put();
        //properties.put();

        if(UI.getCurrent().getSession().getAttribute("cont_asientos") != null)
            asientos = (int) UI.getCurrent().getSession().getAttribute("cont_asientos");
        if(UI.getCurrent().getSession().getAttribute("colu") != null)
            columna = (int)UI.getCurrent().getSession().getAttribute("colu");
        if(UI.getCurrent().getSession().getAttribute("fila") != null)
            fila = (int)UI.getCurrent().getSession().getAttribute("fila");
        if(UI.getCurrent().getSession().getAttribute("lista") != null)
            l2 = (List<Integer>) UI.getCurrent().getSession().getAttribute("lista");
        if(UI.getCurrent().getSession().getAttribute("proyid") != null) {
            proyeccion = (Proyeccion) UI.getCurrent().getSession().getAttribute("proyid");
        }
        if(UI.getCurrent().getSession().getAttribute("horapeli") != null)
            fecha_entrada = (LocalDateTime) UI.getCurrent().getSession().getAttribute("horapeli");

        if(UI.getCurrent().getSession().getAttribute("ofertita") != null)
        ofertaele = (Oferta)UI.getCurrent().getSession().getAttribute("ofertita");

        ofertaname = (String)UI.getCurrent().getSession().getAttribute("idoferta");

        h2.setText("Butacas");

        UserDetails userLogged;
        userLogged = securityUtils.getAuthenticatedUser();
        persona = personaService.findByUsername(userLogged.getUsername());
        dueño = new H2();
        dueño.setText("Dueño de las entradas: "+persona.getUsername()+" "+persona.getApellido());
        add(dueño);

        butacones = l2.size();
        add(h2);
        precio = (butacones/2) * Double.valueOf(proyeccion.getPrecio());
        h4 = new H2();
        h4.setText("El precio es: "+String.valueOf(precio));
        for(int i = 0; i < butacones; i+=2){
            h3 = new H4();
            h3.setText("Fila: "+l2.get(i).toString()+" Butaca: "+l2.get(i+1).toString());
            add(h3);
        }
        if(UI.getCurrent().getSession().getAttribute("ofertita") != null){
            h = new H2();
        h.setText("Oferta elegida: "+ofertaele.getNumero());
        precio += Double.valueOf(ofertaele.getPrecio());
        h4.setText("Precio final: "+String.valueOf(precio)+"€");
        precio = 0;
        add(h);}


        save.setClassName("pointer");
        save.addClickListener(e -> {
            for(int i = 0; i<l2.size(); i+=2 ) {
                entrada = new Entrada();
                entrada.setProyeccion(proyeccion);
                entrada.setFecha_entrada(fecha_entrada);
                entrada.setPersona_ent(persona);
                entrada.setColumna(l2.get(i));
                entrada.setFila(l2.get(i+1));
                posicion = " Fila: "+l2.get(i+1)+" Butaca: "+l2.get(i);
                compradas.add(j,posicion);
                j++;
                entradaService.update(this.entrada);
            }
            for(int z = 0; z < compradas.size(); z++)
                butacas += compradas.get(z);
            triggerMail();
            Notification.show("Datos de la entrada guardado.");
                UI.getCurrent().navigate(ImageListView.class);

        });

        setSpacing(false);

        add(h4);
        add(save);

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
        columna = (int)UI.getCurrent().getSession().getAttribute("colu");
        fila = (int)UI.getCurrent().getSession().getAttribute("fila");
        col2 = (int)UI.getCurrent().getSession().getAttribute("colu");
        fil2 = (int)UI.getCurrent().getSession().getAttribute("fila");
        fecha_entrada = (LocalDateTime) UI.getCurrent().getSession().getAttribute("horapeli");
    }

    public void triggerMail(){
        emailSenderService.SendSimpleMessage("discovercinemaservice@gmail.com",
                "Datos de su entrada",
                "Nombre: "+entrada.getPersona_ent().getUsername()+" "+entrada.getPersona_ent().getApellido()+"\n"+"Pelicula: "+entrada.getProyeccion().getPelicula().getNombre()+"\n"+
                "Dia: "+entrada.getFecha_entrada().getDayOfMonth()+"/"+entrada.getFecha_entrada().getMonth()+"/"+entrada.getFecha_entrada().getYear()+"\n"+"Hora: "+entrada.getFecha_entrada().getHour()+
                ":"+entrada.getFecha_entrada().getMinute()+"\n"+"Sala: "+ entrada.getProyeccion().getSala().getNum_sala() + "\n"+butacas+"\n");
    }

}


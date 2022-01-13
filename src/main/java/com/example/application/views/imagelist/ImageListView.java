package com.example.application.views.imagelist;

import com.example.application.classes.Cine;
import com.example.application.classes.Pelicula;
import com.example.application.repositories.CineService;
import com.example.application.repositories.PeliculaService;
import com.example.application.repositories.ProyeccionService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.littemplate.LitTemplate;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

@PageTitle("Cartelera")
@Route(value = "", layout = MainLayout.class)
@Tag("image-list-view")
@JsModule("./views/imagelist/image-list-view.ts")
public class ImageListView extends LitTemplate implements HasComponents, HasStyle {
    private PeliculaService peliculaService;
    private List<Pelicula> pelis;
    private Button ver_info = new Button("Ver información");
    private ComboBox<Cine> cine;
    private DatePicker fecha;
    private CineService cineService;
    private ProyeccionService proyeccionService;
    public ImageListView(@Autowired PeliculaService peliculas, CineService cineService, ProyeccionService proyeccionService) {
        this.cineService = cineService;
        this.proyeccionService = proyeccionService;
        addClassNames("image-list-view", "flex", "flex-col", "h-full");
        this.peliculaService = peliculas;
        pelis = peliculaService.findAll();

        cine = new ComboBox<Cine>("Cine");
        cine.setItems(this.cineService.findByVisible(true));
        cine.setItemLabelGenerator(Cine::getNombre);
        cine.setValue(this.cineService.findByVisible(true).get(0));
        fecha = new DatePicker("Fecha de la sesion");
        fecha.setMin(LocalDate.now());
        fecha.setValue(LocalDate.now());
        fecha.addValueChangeListener(e -> mostrar(e.getValue(),cine.getValue().getId_cine()));
        List<Cine> l = this.cineService.findByVisible(true);
        cine.setItems(this.cineService.findByVisible(true));
        cine.setItemLabelGenerator(Cine::getNombre);
        cine.setValue(this.cineService.findByVisible(true).get(0));
        cine.addValueChangeListener(e -> mostrar(fecha.getValue(), e.getValue().getId_cine()));

        add(cine,fecha);
        mostrar(fecha.getValue(),cine.getValue().getId_cine());
    }
    public void mostrar(LocalDate f, int id){
        removeAll();
        add(cine,fecha);
        for (Pelicula p : pelis) {
            if(this.proyeccionService.buscasesion(p,f,id))
                    add(new ImageCard(p.getUrl(), p.getNombre(), p.getSinopsis(), p.getGenero(), p.getId_pelicula()));
            }
        }
    }

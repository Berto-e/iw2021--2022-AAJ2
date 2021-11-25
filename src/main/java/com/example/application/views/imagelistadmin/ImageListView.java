package com.example.application.views.imagelistadmin;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Cartelera")
@Route(value = "cartelera-admin", layout = MainLayout.class)
@Tag("image-list-view")
@JsModule("./views/imagelist/image-list-view.ts")
public class ImageListView extends LitTemplate implements HasComponents, HasStyle {

    public ImageListView() {

        add(new ImageCard("Viernes 13","Snow mountains under stars",
                "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/viernes-13-saga-peor-a-mejor-ranking-1588849712.jpeg?crop=1.00xw:0.708xh;0,0&resize=640:*","pelicula de miedo"));
        add(new ImageCard("Halloween","Snow covered mountain",
                "https://miradasdecine.es/data/2020/10/A1WHvTmjLBL._AC_SL1500_-225x300.jpg","pelicula de terror"));
        add(new ImageCard("Rio en montañita","River between mountains",
                "https://images.unsplash.com/photo-1536048810607-3dc7f86981cb?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=375&q=80","ejemplo 3"));
        add(new ImageCard("Via lactea","Milky way on mountains",
                "https://images.unsplash.com/photo-1515705576963-95cad62945b6?ixlib=rb-1.2.1&ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&auto=format&fit=crop&w=750&q=80", "ejemplo 4"));
        add(new ImageCard("Niebla en montañita","Mountain with fog",
                "https://images.unsplash.com/photo-1513147122760-ad1d5bf68cdb?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1000&q=80","ejemplo 5"));
        add(new ImageCard("Montañota de noche","Mountain at night",
                "https://images.unsplash.com/photo-1562832135-14a35d25edef?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=815&q=80", "ejemplo 6"));
    }
}
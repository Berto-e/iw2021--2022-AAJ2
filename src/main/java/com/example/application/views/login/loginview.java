package com.example.application.views.login;

import com.example.application.views.MainLayout;
import com.example.application.views.gestores.gestorview;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Login")
@Route(value = "Register", layout = MainLayout.class)
public class loginview extends Composite<LoginOverlay> {
    public loginview(){
        LoginOverlay component = getContent();
        component.setTitle("Cines Universe");
        component.setDescription("Sitio guapo");
        component.setOpened(true);
        component.addLoginListener(e -> {
            UI.getCurrent().navigate(gestorview.class);
        });
    }
}

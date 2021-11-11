package com.example.application.views.login;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Login")
@Route(value = "Register", layout = MainLayout.class)
public class loginview extends VerticalLayout {
    public loginview(){
        LoginOverlay component = new LoginOverlay();
        component.addLoginListener(e -> component.close());
        Button open = new Button("Login",
                e -> component.setOpened(true));

        LoginI18n i18n = LoginI18n.createDefault();
        i18n.setAdditionalInformation("Bienvenido a nuestros cines");
        component.setI18n(i18n);

        add(component, open);
    }
}

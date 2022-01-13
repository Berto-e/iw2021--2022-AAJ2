package com.example.application.views;

import com.example.application.security.CustomRequestCache;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

@Tag("sa-login-view")
@Route(value = LoginView.ROUTE)
@PageTitle("Login")
public class LoginView extends VerticalLayout {
    public static final String ROUTE = "login";
    private LoginOverlay login = new LoginOverlay();

    private AuthenticationManager authenticationManager;
    private CustomRequestCache requestCache;

    @Autowired
    public LoginView(AuthenticationManager authenticationManager, CustomRequestCache requestCache) {
        this.authenticationManager = authenticationManager;
        this.requestCache = requestCache;
        login.setOpened(true);
        login.setTitle("Bienvenido");
        login.setDescription("Login");

        login.addLoginListener(e -> this.authenticate(e.getUsername(), e.getPassword()));
        login.addForgotPasswordListener(event -> {
            login.close();
            UI.getCurrent().navigate(PassRecover.class);
        });

        add(login);
    }


    private void authenticate(String username, String password) {
        try{
            // try to authenticate with given credentials, should always return not null or
            //throw an {@link AuthenticationException}
            final Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password)); //
            //if authentication was successful we will update the security context and
            //redirect to the page requested first
            SecurityContextHolder.getContext().setAuthentication(authentication); //
            login.close();
            UI.getCurrent().navigate(ImageListView.class); //

        }catch (AuthenticationException ex) {
            login.setError(true);
        }
    }

}

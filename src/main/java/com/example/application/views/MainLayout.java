package com.example.application.views;

import java.util.ArrayList;
import java.util.List;

import com.example.application.repositories.SecurityService;
import com.example.application.security.SecurityUtils;
import com.example.application.views.gestores.gestorview;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;


import com.example.application.views.imagelist.ImageListView;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.VaadinServlet;
import com.vaadin.flow.theme.Theme;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.swing.text.html.ListView;

/**
 * The main view is a top-level placeholder for other views.
 */
@PWA(name = "My App", shortName = "My App",
        backgroundColor = "#227aef", themeColor = "#227aef",
        enableInstallPrompt = false)
@Theme(themeFolder = "myapp")
@PageTitle("Main")
public class MainLayout extends AppLayout {
    private final Tabs menu;
    private Button button = new Button("Log in");
    private final SecurityService securityService;
    private SecurityUtils securityUtils = new SecurityUtils();
    public MainLayout(SecurityService securityService) {
        this.securityService = securityService;

            //Login Button
           button.addClickListener(e2 -> UI.getCurrent().navigate(LoginView.class));
           HorizontalLayout Login = new HorizontalLayout(button);
           //e_Login

        this.setDrawerOpened(false);
        Span appName = new Span("Cartelera Discover");
        appName.addClassName("hide-on-mobile");

        menu = createMenuTabs();

        this.addToNavbar(appName);
        this.addToNavbar(true, menu);

        if(securityUtils.isUserLoggedIn()) {
            //Logout Button
            Button logout = new Button("Log out", e -> securityService.logout());
            HorizontalLayout Logout = new HorizontalLayout(logout);
            Logout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.END);
            //userMenu
            UserDetails userLogged;
            userLogged = securityUtils.getAuthenticatedUser();
            MenuBar menuBar = new MenuBar();
            Text selected = new Text("");


            MenuItem share = menuBar.addItem(userLogged.getUsername());
            SubMenu shareSubMenu = share.getSubMenu();
            shareSubMenu.addItem("Logout", e -> securityService.logout());
            HorizontalLayout User = new HorizontalLayout(menuBar);
            this.addToNavbar(User);
            //e_userMenu
            //e_Logout
        }
        else
            this.addToNavbar(Login); //LOGIN BUTTON
        getElement().addEventListener("search-focus", e -> {
            getElement().getClassList().add("hide-navbar");
        });

        getElement().addEventListener("search-blur", e -> {
            getElement().getClassList().remove("hide-navbar");
        });
    }




    private static Tabs createMenuTabs() {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.HORIZONTAL);
        tabs.add(getAvailableTabs());
        return tabs;
    }

    private static Tab[] getAvailableTabs() {
        final List<Tab> tabs = new ArrayList<>(4);
        tabs.add(createTab(VaadinIcon.EDIT, "Cartelera",
                ImageListView.class));
        tabs.add(createTab(VaadinIcon.CLOCK,"Gestores", gestorview.class));
        tabs.add(createTab(VaadinIcon.USER,"usuarios", ImageListView.class));
        tabs.add(createTab(VaadinIcon.CALENDAR, "productos", ImageListView.class));

        final String contextPath = VaadinServlet.getCurrent().getServletContext().getContextPath();
        return tabs.toArray(new Tab[tabs.size()]);
    }

    private static Tab createTab(VaadinIcon icon, String title, Class<? extends Component> viewClass) {
        return createTab(populateLink(new RouterLink(null, viewClass), icon, title));
    }

    private static Tab createTab(Component content) {
        final Tab tab = new Tab();
        tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        tab.add(content);
        return tab;
    }

    private static <T extends HasComponents> T populateLink(T a, VaadinIcon icon, String title) {
        a.add(icon.create());
        a.add(title);
        return a;
    }


}
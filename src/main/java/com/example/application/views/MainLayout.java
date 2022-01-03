package com.example.application.views;

import java.util.ArrayList;
import java.util.List;

import com.example.application.views.gestores.gestorview;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.VaadinServlet;
import com.vaadin.flow.theme.Theme;

import com.example.application.views.imagelist.ImageListView;

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

    public MainLayout() {
        //Logout Button
        //this.securityService = securityService;
        //Button logout = new Button("Log out", e -> securityService.logout());
        //HorizontalLayout Logout = new HorizontalLayout(logout);
        //Logout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.END);
        //e_Logout
        this.setDrawerOpened(false);
        Span appName = new Span("Cartelera Discover");
        appName.addClassName("hide-on-mobile");

        menu = createMenuTabs();

        this.addToNavbar(appName);
        this.addToNavbar(true, menu);
        //this.addToNavbar(Logout); //LOGOUT BUTTON
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
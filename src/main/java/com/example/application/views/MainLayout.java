package com.example.application.views;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.application.views.login.loginview;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Nav;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.example.application.views.MainLayout;
import com.example.application.views.helloworld.HelloWorldView;
import com.example.application.views.about.AboutView;
import com.example.application.views.imagelist.ImageListView;
import com.vaadin.flow.component.avatar.Avatar;

/**
 * The main view is a top-level placeholder for other views.
 */
@PWA(name = "My App", shortName = "My App",
        backgroundColor = "#227aef", themeColor = "#227aef",
        enableInstallPrompt = false)
@Theme(themeFolder = "myapp")
@PageTitle("Main")
public class MainLayout extends AppLayout {

    public static class MenuItemInfo {

        private String text;
        private String iconClass;
        private Class<? extends Component> view;

        public MenuItemInfo(String text, String iconClass, Class<? extends Component> view) {
            this.text = text;
            this.iconClass = iconClass;
            this.view = view;
        }

        public String getText() {
            return text;
        }

        public String getIconClass() {
            return iconClass;
        }

        public Class<? extends Component> getView() {
            return view;
        }

    }

    private H1 viewTitle;

    public MainLayout() {
        setPrimarySection(Section.NAVBAR);

        addToNavbar(createDrawerContent());
    }

    private Component createDrawerContent() {
        H1 title = new H1("Cartelera nuestra");
        title.getStyle()
                .set("font-size", "var(--lumo-font-size-l)")
                .set("left", "var(--lumo-space-l)")
                .set("margin", "0")
                .set("position", "absolute");
        Tabs tabs = new Tabs();
        FlexLayout centeredLayout = new FlexLayout();
        centeredLayout.setSizeFull();
        centeredLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        centeredLayout.add(tabs);
        Tab tib = new Tab(new RouterLink("Login", loginview.class));
        Tab tab = new Tab(new RouterLink("Peliculas", ImageListView.class));
        Tab tub = new Tab(new RouterLink("About", AboutView.class));


        tabs.add(tib,tab,tub);
        addToNavbar(title,centeredLayout,tabs);
        return tabs;
    }

    private Nav createNavigation() {
        Nav nav = new Nav();
        nav.addClassNames("border-b", "border-contrast-10", "flex-grow", "overflow-auto");
        nav.getElement().setAttribute("aria-labelledby", "views");

        H3 views = new H3("Views");
        views.addClassNames("flex", "h-m", "items-center", "mx-m", "my-0", "text-s", "text-tertiary");
        views.setId("views");

        // Wrap the links in a list; improves accessibility
        UnorderedList list = new UnorderedList();
        list.addClassNames("list-none", "m-0", "p-0");
        nav.add(list);

        for (RouterLink link : createLinks()) {
            ListItem item = new ListItem(link);
            list.add(item);
        }
        return nav;
    }

    private List<RouterLink> createLinks() {
        MenuItemInfo[] menuItems = new MenuItemInfo[]{ //
                new MenuItemInfo("Hello World", "la la-globe", HelloWorldView.class), //

                new MenuItemInfo("About", "la la-file", AboutView.class), //

                new MenuItemInfo("Image List", "la la-th-list", ImageListView.class), //
                new MenuItemInfo("Reister","la la-file", loginview.class)

        };
        List<RouterLink> links = new ArrayList<>();
        for (MenuItemInfo menuItemInfo : menuItems) {
            links.add(createLink(menuItemInfo));

        }
        return links;
    }

    private static RouterLink createLink(MenuItemInfo menuItemInfo) {
        RouterLink link = new RouterLink();
        link.addClassNames("flex", "mx-s", "p-s", "relative", "text-secondary");
        link.setRoute(menuItemInfo.getView());

        Span icon = new Span();
        icon.addClassNames("me-s", "text-l");
        if (!menuItemInfo.getIconClass().isEmpty()) {
            icon.addClassNames(menuItemInfo.getIconClass());
        }

        Span text = new Span(menuItemInfo.getText());
        text.addClassNames("font-medium", "text-s");

        link.add(icon, text);
        return link;
    }

    private Footer createFooter() {
        Footer layout = new Footer();
        layout.addClassNames("flex", "items-center", "my-s", "px-m", "py-xs");

        return layout;
    }


    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}

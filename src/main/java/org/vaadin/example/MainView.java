package org.vaadin.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.react.ReactRouterOutlet;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.server.menu.AvailableViewInfo;
import com.vaadin.flow.server.menu.MenuRegistry;


/**
 * The main view is a top-level placeholder for other views.
 */
// TODO: create layout field to hilla ViewConfig so that a server side layout can be defined
// To use current implementation add `"layout": ""` for hilla routes in file-routes.json
    // Note! also login requires the layout as the current routes.tsx always adds a server layout
@AnonymousAllowed
@Layout
public class MainView extends AppLayout {
    private final Tabs menu;
    private H1 viewTitle;

    public MainView() {
        // Use the drawer for the menu
        setPrimarySection(Section.DRAWER);

        // Make the nav bar a header
        addToNavbar(true, createHeaderContent());

        // Put the menu in the drawer
        menu = createMenu();
        addToDrawer(createDrawerContent(menu));
    }

    private Component createHeaderContent() {
        HorizontalLayout layout = new HorizontalLayout();

        // Configure styling for the header
        layout.setId("header");
        layout.getThemeList().set("dark", true);
        layout.setWidthFull();
        layout.setSpacing(false);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        // Have the drawer toggle button on the left
        layout.add(new DrawerToggle());

        // Placeholder for the title of the current view.
        // The title will be set after navigation.
        viewTitle = new H1();
        layout.add(viewTitle);

        // A user icon
//        layout.add(new Image("images/user.svg", "Avatar"));

        return layout;
    }

    private Component createDrawerContent(Tabs menu) {
        VerticalLayout layout = new VerticalLayout();

        // Configure styling for the drawer
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        layout.getThemeList().set("spacing-s", true);
        layout.setAlignItems(FlexComponent.Alignment.STRETCH);

        // Have a drawer header with an application logo
        HorizontalLayout logoLayout = new HorizontalLayout();
        logoLayout.setId("logo");
        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
//        logoLayout.add(new Image("images/logo.png", "My Project logo"));
        logoLayout.add(new H1("My Project"));

        // Display the logo and the menu in the drawer
        layout.add(logoLayout, menu, new Anchor("login", "Login"));
        return layout;
    }

    private Tabs createMenu() {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
        tabs.setId("tabs");
        tabs.add(createMenuItems());
        return tabs;
    }

    private Component[] createMenuItems() {
        Map<String, AvailableViewInfo> views = MenuRegistry.collectMenuItems();
        List<Tab> tabs = new ArrayList<>();
        for(String key: views.keySet()) {
            if(views.get(key).route().equals("login")) {
                continue;
            }
            tabs.add(createTab(views.get(key).title(), views.get(key).route()));
        }
        return tabs.toArray(new Component[tabs.size()]);
    }

    private static Tab createTab(String text, String link) {
        final Tab tab = new Tab();
        tab.add(new Anchor(link, text));
        ComponentUtil.setData(tab, String.class, link);
        return tab;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();

        // Select the tab corresponding to currently shown view
        getTabForComponent().ifPresent(menu::setSelectedTab);

        // Set the view title in the header
        viewTitle.setText(getCurrentPageTitle());
    }

    private Optional<Tab> getTabForComponent() {
        UI ui = UI.getCurrent();
        String currentPath = ui.getActiveViewLocation().getPath();
        return menu.getChildren()
                .filter(tab -> currentPath
                        .equals(ComponentUtil.getData(tab, String.class)))
                .findFirst().map(Tab.class::cast);
    }

    private String getCurrentPageTitle() {
        if(getContent() == null || getContent().getClass().equals(
                ReactRouterOutlet.class)) {
            return getClientTitle();
        }
        String titleValue = getContent().getClass().getAnnotation(PageTitle.class)
                .value();
        return titleValue.isEmpty() ? getClientTitle() : titleValue;
    }

    private String getClientTitle() {
        UI ui = UI.getCurrent();
        String currentPath = ui.getActiveViewLocation().getPath();
        Map<String, AvailableViewInfo> clientMenuItems = MenuRegistry.collectClientMenuItems(
                true, VaadinSession.getCurrent().getConfiguration());
        Optional<String> clientView = clientMenuItems.keySet().stream()
                .filter(key -> key.equals(currentPath) || key.equals("/"+currentPath)).findFirst();
        if(clientView.isPresent()) {
            return clientMenuItems.get(clientView.get()).menu().title();
        }

        return "";
    }
}
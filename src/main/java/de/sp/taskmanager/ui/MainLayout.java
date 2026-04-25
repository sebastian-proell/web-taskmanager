package de.sp.taskmanager.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;

/**
 * Haupt-Layout mit verbesserter Navigation (Termin 3).
 *
 * Statt einfacher Text-Links werden jetzt größere Buttons mit Icons verwendet –
 * deutlich ansprechender und benutzerfreundlicher für betriebliche Anwendungen.
 *
 * Good Practice: Icons (VaadinIcon) verbessern die Usability. Buttons mit
 * ButtonVariant.LUMO_PRIMARY machen die Navigation visuell klarer.
 *
 * Für Anfänger: Das Drawer-Menü ist nun ein VerticalLayout mit großen Buttons.
 * Logout-Button wurde hinzugefügt. Alles bleibt serverseitig in Java.
 */
@PermitAll
public class MainLayout extends com.vaadin.flow.component.applayout.AppLayout {

    public MainLayout() {
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 title = new H1("TaskManager");
        title.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.MEDIUM);

        com.vaadin.flow.component.applayout.DrawerToggle toggle = new com.vaadin.flow.component.applayout.DrawerToggle();

        HorizontalLayout header = new HorizontalLayout(toggle, title);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidthFull();
        header.addClassNames(LumoUtility.Padding.Vertical.NONE, LumoUtility.Padding.Horizontal.MEDIUM);

        addToNavbar(header);
    }

    private void createDrawer() {
        // Größere Navigation mit Icons
        Button dashboardBtn = new Button("Dashboard", VaadinIcon.DASHBOARD.create());
        dashboardBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        dashboardBtn.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate(DashboardView.class)));

        Button tasksBtn = new Button("Aufgaben verwalten", VaadinIcon.LIST.create());
        tasksBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        tasksBtn.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate(TaskView.class)));

        Button logoutBtn = new Button("Abmelden", VaadinIcon.SIGN_OUT.create());
        logoutBtn.addThemeVariants(ButtonVariant.LUMO_ERROR);
        logoutBtn.addClickListener(e -> {
            getUI().ifPresent(ui -> ui.getPage().setLocation("/logout"));
        });

        VerticalLayout nav = new VerticalLayout(dashboardBtn, tasksBtn, logoutBtn);
        nav.setPadding(true);
        nav.setSpacing(true);

        addToDrawer(nav);
    }
}
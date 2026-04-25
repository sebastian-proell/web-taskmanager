package de.sp.taskmanager.ui;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

/**
 * Dashboard-View – Startseite der Anwendung.
 *
 * Demonstriert, dass mehrere Views im selben MainLayout existieren können.
 * Dies ist ein zentrales Konzept komponentenbasierter UIs (Lektion 3.1).
 *
 * Good Practice: Jede View ist eine eigenständige, wiederverwendbare Komponente.
 * Vaadin übernimmt das Routing automatisch. Das Dashboard dient als Einstiegspunkt
 * und kann später mit Statistiken oder Übersichten erweitert werden (Termin 5/6).
 *
 * Für Anfänger: @Route("") mit layout = MainLayout.class macht diese View zur
 * Startseite. Der Inhalt wird serverseitig in Java gerendert.
 */
@Route(value = "", layout = MainLayout.class)
@PageTitle("Dashboard | TaskManager")
@PermitAll
public class DashboardView extends VerticalLayout {

    public DashboardView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        H2 welcome = new H2("Willkommen im TaskManager");
        Paragraph intro = new Paragraph("Dies ist die komponentenbasierte Vaadin-Oberfläche (Lektion 3).");
        Paragraph info = new Paragraph("Navigieren Sie über das Menü links zu den Aufgaben.");

        add(welcome, intro, info);
    }
}
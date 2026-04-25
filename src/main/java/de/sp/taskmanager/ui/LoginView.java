package de.sp.taskmanager.ui;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.internal.RouteUtil;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.PermitAll;

/**
 * Login-View für die Vaadin-Anwendung (Termin 3).
 *
 * Demonstriert, wie man eine sichere Login-Seite mit Vaadin erstellt.
 * Wird automatisch von der SecurityConfiguration als Login-Seite verwendet.
 *
 * Good Practice: LoginOverlay oder LoginForm von Vaadin sorgt für ein modernes,
 * barrierefreies Login-Erlebnis. Die View ist @AnonymousAllowed, damit sie
 * ohne Authentifizierung erreichbar ist.
 *
 * Für Anfänger: Diese Klasse zeigt, wie Vaadin-Komponenten (LoginForm) mit
 * Spring Security zusammenarbeiten. Bei falschen Zugangsdaten wird eine
 * Fehlermeldung angezeigt.
 */
@Route("login")
@PageTitle("Login | TaskManager")
@AnonymousAllowed
@PermitAll
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private final LoginForm loginForm = new LoginForm();

    public LoginView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        H1 title = new H1("TaskManager – Login");
        add(title);

        // Deutsche Fehlermeldungen und Texte
        LoginI18n i18n = LoginI18n.createDefault();
        i18n.setHeader(new LoginI18n.Header());
        i18n.getHeader().setTitle("Willkommen");
        i18n.getHeader().setDescription("Melden Sie sich an, um fortzufahren");
        i18n.getForm().setUsername("Benutzername");
        i18n.getForm().setPassword("Passwort");
        i18n.getForm().setSubmit("Anmelden");
        i18n.getErrorMessage().setTitle("Anmeldung fehlgeschlagen");
        i18n.getErrorMessage().setMessage("Bitte prüfen Sie Ihre Zugangsdaten.");
        loginForm.setI18n(i18n);

        loginForm.setForgotPasswordButtonVisible(false);

        loginForm.setAction("login");

        add(loginForm);

        // Action-Listener wird von VaadinWebSecurity automatisch behandelt
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        // Wenn bereits eingeloggt → direkt zur Startseite weiterleiten
        if (beforeEnterEvent.getLocation().getPath().equals("login") &&
                beforeEnterEvent.getUI().getSession().getAttribute("loggedIn") != null) {
            beforeEnterEvent.forwardTo("");
        }
    }
}
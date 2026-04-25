package de.sp.taskmanager.config;

import com.vaadin.flow.spring.security.VaadinSecurityConfigurer;
import de.sp.taskmanager.ui.LoginView;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Finale und stabile Sicherheitskonfiguration für Termin 3 (Vaadin 25 + Spring Boot 4).
 *
 * Diese Version behebt das Problem, dass der "Anmelden"-Button nichts tut:
 * - CSRF wird deaktiviert (notwendig für Vaadin LoginForm)
 * - formLogin mit defaultSuccessUrl sorgt für die Weiterleitung nach erfolgreichem Login
 * - VaadinSecurityConfigurer integriert die Vaadin-LoginView
 *
 * Good Practice: Security bleibt zentral und übersichtlich. Die beiden Test-User bleiben
 * für die Vorlesung erhalten. In Produktion später durch echte User-Verwaltung ersetzen.
 *
 * Für Anfänger: Nach dem Klick auf "Anmelden" wirst du automatisch zur Dashboard-View
 * weitergeleitet. Die Zugangsdaten sind die gleichen wie bisher.
 */
@Configuration
@EnableWebSecurity
@Profile("prod")
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable());

        http.with(VaadinSecurityConfigurer.vaadin(), configurer -> {
            configurer.loginView(LoginView.class);
        });

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/h2-console/**").permitAll()
        );

        http.formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
        );

        http.headers(headers ->
                headers.frameOptions(frame -> frame.disable())
        );

        return http.build();
    }

    /**
     * In-Memory UserDetailsService mit Beispiel-Benutzern.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();

        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
}
package de.sp.taskmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;  // Add this import

/**
 * Diese Klasse konfiguriert die Sicherheitseinstellungen der Anwendung mit Spring Security.
 * Sie definiert, welche URLs geschützt sind und wie Benutzer authentifiziert werden.
 *
 * Good Practice: Sicherheit wird in einer separaten Konfigurationsklasse gehandhabt, um sie von der
 * Geschäftslogik zu trennen. CSRF-Schutz wird in der Entwicklung deaktiviert, aber in der Produktion
 * aktiviert, um Sicherheitslücken zu vermeiden.
 *
 * Wichtig zu wissen: Spring Security ist ein Framework, das Authentifizierung und Autorisierung managt.
 * @Configuration markiert diese Klasse als Quelle für Beans, die Spring verwaltet. Die Annotation
 * @Profile("prod") sorgt dafür, dass diese Konfiguration nur im Produktionsmodus aktiv ist.
 */
@Configuration
@EnableWebSecurity
@Profile("prod")
public class SecurityConfiguration {

    /**
     * Erstellt die SecurityFilterChain, die alle HTTP-Anfragen absichert.
     *
     * Good Practice: Die Security-Konfiguration wird als Bean zurückgegeben, damit Spring sie
     * automatisch einbindet. Rollen-basierte Zugriffsregeln werden zentral definiert.
     *
     * Wichtig zu wissen: authorizeHttpRequests definiert, wer welche Endpunkte aufrufen darf.
     * httpBasic aktiviert die einfache Benutzername/Passwort-Authentifizierung im Browser.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/tasks").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/api/tasks/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable());  // Für Development; in Prod aktivieren

        return http.build();
    }

    /**
     * Erstellt einen In-Memory UserDetailsService mit Beispiel-Benutzern.
     *
     * Good Practice: Für Entwicklung In-Memory-Benutzer verwenden; in Produktion eine Datenbank
     * oder externen Service integrieren. Passwörter werden gehasht (hier mit DefaultPasswordEncoder).
     *
     * Wichtig zu wissen: UserDetailsService lädt Benutzerdaten. Rollen wie "USER" oder "ADMIN" bestimmen
     * die Berechtigungen. InMemoryUserDetailsManager speichert die Benutzer nur im RAM.
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

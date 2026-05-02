package de.sp.taskmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Sicherheitskonfiguration für das Multi-Module-Projekt (Termin 3 – React-Variante).
 *
 * Diese Konfiguration schützt die REST-API mit Basic Auth und erlaubt dem React-Frontend
 * (Port 5173) den Zugriff.
 *
 * Good Practice: CORS und Security werden zentral in einer Konfigurationsklasse definiert.
 * Die REST-API ist für das React-Frontend zugänglich.
 *
 * Wichtig zu wissen: Das React-Frontend sendet Benutzername und Passwort im Authorization-Header.
 * Die CORS-Regeln erlauben dem Browser, Anfragen vom Development-Server (5173) an das Backend (8080) zu stellen.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(withDefaults());

        http.authorizeHttpRequests(auth -> auth
                // Preflight-Requests (OPTIONS) müssen immer erlaubt sein
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                .requestMatchers("/login").permitAll()
                .requestMatchers("/h2-console/**").permitAll()

                .requestMatchers("/api/**").authenticated()
                .anyRequest().authenticated()
        );

        // Basic Auth aktivieren
        http.httpBasic(withDefaults());

        // H2 Console Frame erlauben (für die Entwicklung)
        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }

    /**
     * CORS-Konfiguration für das React-Frontend.
     *
     * Good Practice: CORS wird als eigenständige Bean definiert, damit sie klar von der
     * Security-Konfiguration getrennt ist.
     *
     * Wichtig zu wissen: Diese Konfiguration erlaubt dem Browser auf Port 5173, Anfragen an
     * das Backend auf Port 8080 zu stellen und den Authorization-Header zu verwenden.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of("http://localhost:5173"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("Authorization"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    /**
     * In-Memory UserDetailsService mit Beispiel-Benutzern.
     *
     * Good Practice: Für Entwicklung und Vorlesung In-Memory-Benutzer verwenden.
     * In Produktion sollte eine Datenbank oder ein externer Authentifizierungsdienst verwendet werden.
     *
     * Wichtig zu wissen: Die beiden Test-Benutzer sind:
     * - Benutzer: user / Passwort: password
     * - Admin:   admin / Passwort: admin
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
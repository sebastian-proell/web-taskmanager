package de.sp.taskmanager.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Diese Klasse stellt eine Test-spezifische Sicherheitskonfiguration bereit.
 * Sie wird nur verwendet, wenn das Profil "test" aktiv ist.
 *
 * Good Practice: Separate Security-Konfiguration für Tests, damit Tests ohne echte Authentifizierung laufen können.
 * Dadurch bleiben die Tests schnell und unabhängig von Produktions-Sicherheitseinstellungen.
 *
 * Wichtig zu wissen: @TestConfiguration markiert diese Klasse als Test-Bean-Konfiguration.
 * @Profile("test") sorgt dafür, dass diese Konfiguration nur während der Tests aktiv ist.
 * @Import wird in den Integrationstests verwendet, um diese Konfiguration einzubinden.
 */
@TestConfiguration
@EnableWebSecurity
@Profile("test")
public class TestSecurityConfig {

    /**
     * Erstellt die SecurityFilterChain für Tests.
     *
     * Good Practice: In Tests werden alle API-Endpunkte freigegeben (/api/**.permitAll()), damit keine
     * Authentifizierung die Testausführung behindert.
     *
     * Wichtig zu wissen: Die Security-Konfiguration für Tests deaktiviert CSRF und erlaubt alle
     * API-Aufrufe ohne Benutzername und Passwort. Das erleichtert das Testen mit MockMvc.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").permitAll()   // Alle API-Endpunkte für Tests freigeben
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable());               // CSRF für Tests deaktivieren

        return http.build();
    }
}

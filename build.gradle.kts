/*
 * BUILD-KONFIGURATION MIT GRADLE (build.gradle.kts)
 *
 * Diese Datei steuert den gesamten Build-Prozess des Projekts. Sie definiert, welche Plugins verwendet werden,
 * welche Java-Version eingesetzt wird und welche Bibliotheken (Dependencies) das Projekt benötigt.
 *
 * Good Practice: Alle Abhängigkeiten und Konfigurationen werden zentral in einer Datei verwaltet.
 * Dadurch ist das Projekt auf jedem Rechner reproduzierbar und sofort lauffähig.
 *
 * Wichtig zu wissen: Gradle ist ein Build-Tool, das automatisch Bibliotheken herunterlädt und den Code
 * kompiliert. Die Datei endet auf .kts, weil hier die Kotlin-DSL verwendet wird – eine moderne und typsichere Variante.
 */

plugins {
    id("java")
    id("org.springframework.boot") version "4.0.5"
    id("io.spring.dependency-management") version "1.1.6"
}

val vaadinVersion = "25.1.1"   // passt zu Spring Boot 4.0.5 + Java 21 (kompatibel laut Vaadin-Doku 2026)


allprojects {
    group = "de.sp.taskmanager"
    version = "1.0.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }
    dependencyManagement {
        imports {
            mavenBom("com.vaadin:vaadin-bom:$vaadinVersion")
        }
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
    sourceCompatibility = JavaVersion.VERSION_21  // Explizit gesetzt für Kompatibilität mit Java 21.
    targetCompatibility = JavaVersion.VERSION_21
}



/**
 * ABHÄNGIGKEITEN (Dependencies) – das Herzstück jeder Spring-Boot-Anwendung
 *
 * Good Practice: Alle benötigten Bibliotheken werden zentral in einer Datei verwaltet.
 * Dadurch ist das Projekt auf jedem Rechner (auch bei eurem Praxispartner) sofort lauffähig.
 *
 * Wichtig zu wissen: Spring Boot bringt „Starter“ mit – das sind fertige Pakete, die viele Dinge automatisch für euch erledigen.
 * Beispiel: spring-boot-starter-web startet sofort einen Webserver und kann JSON verarbeiten.
 */
dependencies {
    // Laufzeit-Dependencies
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")

    // Vaadin für Termin 3 (komponentenbasierte Web-Benutzerschnittstellen)
    implementation("com.vaadin:vaadin-spring-boot-starter")
    developmentOnly("com.vaadin:vaadin-dev-server")   // ← NEU & WICHTIG für Dev-Mode


    runtimeOnly("com.h2database:h2")  // In-Memory-DB für Development
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:3.0.3")  // Für Swagger-UI (optional, für API-Docs)

    // Test-Dependencies
    testImplementation("org.springframework.boot:spring-boot-starter-test")  // Enthält JUnit 5, Mockito, etc. für Unit- und Integrationstests (inkl. @MockBean)
    testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")  // Hinzugefügt
    testImplementation("org.springframework.boot:spring-boot-starter-data-jpa-test")  // Hinzugefügt für @DataJpaTest und JPA-Test-Support
    testImplementation("org.springframework.security:spring-security-test")  // Für Security-Tests.
    testImplementation("org.mockito:mockito-junit-jupiter:5.14.2")  // Explizit für Mockito-Extension in Tests (falls nicht in starter-test enthalten).
    testImplementation("org.springframework.boot:spring-boot-test-autoconfigure")  // Explizit hinzugefügt für Autokonfiguration von Test-Annotations wie @WebMvcTest, @DataJpaTest usw.

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")  // Für JUnit-Plattform in Tests.
}

tasks.withType<Test> {
    useJUnitPlatform()  // Aktiviert JUnit 5 für alle Tests.
    testLogging {  // Logging für bessere Fehlersuche bei Testausfällen.
        events("passed", "skipped", "failed")
    }
}
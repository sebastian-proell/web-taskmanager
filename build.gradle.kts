plugins {
    java                // Aktiviert die Java-Unterstützung in Gradle
    id("application")   // <-- Wichtig: so muss es in Kotlin DSL heißen
}

allprojects {
    group = "de.sp.web-taskmanager"
    version = "1.0.0-SNAPSHOT"

    // Java-Version festlegen
    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(21)
        }
    }

    repositories {
        mavenCentral()  // Wo Gradle die Bibliotheken herunterlädt
    }

    dependencies {
        // Test-Bibliothek – nur für Tests sichtbar
        testImplementation("org.junit.jupiter:junit-jupiter:5.11.3")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    }

    tasks.test {
        useJUnitPlatform()  // Sagt Gradle, dass wir JUnit 5 verwenden
    }

    // === Gradle Run Task ===
    application {
        // Hier wird die Main-Klasse festgelegt, die beim Befehl ./gradlew run gestartet wird
        mainClass.set("de.sp.taskmanager.TaskManager")
    }
}
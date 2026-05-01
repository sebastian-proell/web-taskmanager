/**
 * build.gradle.kts – Root-Build-Konfiguration des Multi-Module-Projekts.
 *
 * Diese Datei enthält die gemeinsamen Einstellungen für alle Module (spring und frontend).
 * Plugins, die in mehreren Modulen benötigt werden, werden hier mit „apply false“ deklariert,
 * damit die einzelnen Module sie bei Bedarf aktivieren können.
 *
 * Good Practice: Gemeinsame Konfigurationen (Java-Version, Repositories, Group/Version)
 * werden zentral im Root definiert. Das verhindert Duplizierung und erleichtert Wartung.
 *
 * Für Anfänger: Diese Datei ist die „Schaltzentrale“ des gesamten Projekts. Hier werden
 * die Java-Toolchain (Version 21) und die allgemeinen Repositories festgelegt.
 */
plugins {
    id("org.springframework.boot") version "4.0.5" apply false
    id("io.spring.dependency-management") version "1.1.6" apply false
}

allprojects {
    group = "de.sp.taskmanager"
    version = "1.0.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

/**
 * Zentraler Clean-Task für das gesamte Projekt.
 *
 * Dieser Task löscht sowohl die Gradle-Artefakte des Spring-Backends als auch
 * die Build-Artefakte des React-Frontends (node_modules, dist etc.).
 *
 * Good Practice: Ein zentraler Clean-Task vereinfacht die Entwicklung und verhindert
 * Inkonsistenzen zwischen Backend und Frontend.
 */
tasks.register("cleanAll") {
    group = "build"
    description = "Komplettes Clean des gesamten Projekts (backend + Frontend)"
    dependsOn(":backend:clean", ":frontend:frontendClean")
}
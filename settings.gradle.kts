/**
 * settings.gradle.kts – Konfigurationsdatei für das Multi-Module-Projekt.
 *
 * Diese Datei definiert den Namen des Root-Projekts und alle Submodule (spring und frontend).
 * Sie wird von Gradle beim ersten Build automatisch gelesen und legt die Struktur des
 * gesamten Projekts fest.
 *
 * Good Practice: In Multi-Module-Projekten werden alle Module hier zentral eingebunden.
 * Dadurch bleibt die Projektstruktur übersichtlich und erweiterbar (z. B. später für
 * weitere Module wie „mobile“ oder „e2e-tests“).
 *
 * Für Anfänger: Jedes `include("...")` entspricht einem Unterordner im Root-Verzeichnis.
 * Der Root-Name „taskmanager“ wird für die Gradle-Builds verwendet.
 */
rootProject.name = "taskmanager"

include("backend")
include("frontend")
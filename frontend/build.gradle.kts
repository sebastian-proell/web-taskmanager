import com.github.gradle.node.npm.task.NpmTask

plugins {
    id("com.github.node-gradle.node") version "7.1.0"
}

/**
 * Gradle-Konfiguration für das React-Frontend-Modul (Termin 3).
 *
 * Diese Datei integriert Node.js und npm in das Gradle-Multi-Module-Projekt.
 * Die Tasks sind so verknüpft, dass bei `frontendDev` automatisch alle
 * notwendigen Voraussetzungen (Install + ggf. Build) ausgeführt werden.
 */
node {
    version = "24.15.0"
    npmVersion = "11.12.1"
    download = true
    workDir = file("../.gradle/nodejs")
    npmWorkDir = file("../.gradle/npm")
}

// =====================================================
// Tasks mit automatischer Abhängigkeitsverknüpfung
// =====================================================

/**
 * Installiert alle npm-Abhängigkeiten.
 * Wird automatisch ausgeführt, wenn `frontendDev` oder `frontendBuild` aufgerufen wird.
 */
tasks.register<NpmTask>("frontendInstall") {
    group = "frontend"
    description = "Installiert alle npm-Abhängigkeiten"
    args.set(listOf("install"))
    workingDir.set(projectDir)
}

/**
 * Baut das React-Frontend für die Produktion.
 * Hängt automatisch von `frontendInstall` ab.
 */
tasks.register<NpmTask>("frontendBuild") {
    group = "frontend"
    description = "Baut das React-Frontend (Production Build)"
    dependsOn("frontendInstall")
    args.set(listOf("run", "build"))
    workingDir.set(projectDir)
}

/**
 * Startet den Development-Server (Vite).
 * Hängt automatisch von `frontendInstall` ab.
 * → Beim ersten Start wird automatisch `npm install` ausgeführt.
 */
tasks.register<NpmTask>("frontendDev") {
    group = "frontend"
    description = "Startet den React Development Server (Vite)"
    dependsOn("frontendInstall")
    args.set(listOf("run", "dev"))
    workingDir.set(projectDir)
}

/**
 * Löscht alle Build-Artefakte und node_modules.
 */
tasks.register("frontendClean") {
    group = "frontend"
    description = "Löscht node_modules, dist und alle Build-Artefakte"
    doLast {
        project.delete("node_modules")
        project.delete("dist")
        project.delete(".vite")
        println("✅ Frontend wurde vollständig gecleant.")
    }
}
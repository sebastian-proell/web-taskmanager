/// <reference types="vite/client" />

/**
 * TypeScript-Definitionen für Vite Environment-Variablen.
 *
 * Diese Datei macht alle VITE_*-Variablen aus der .env-Datei im TypeScript-Code
 * für die IDE sichtbar und vermeidet TypeScript-Fehler.
 *
 * Good Practice: Environment-Variablen werden zentral typisiert, damit die IDE
 * Autovervollständigung und Typprüfung bieten kann.
 *
 * Für Anfänger: Ohne diese Datei zeigt die IDE oft rote Fehler an, obwohl der Build
 * mit Vite erfolgreich ist.
 */
interface ImportMetaEnv {
    readonly VITE_API_USERNAME: string
    readonly VITE_API_PASSWORD: string
}

interface ImportMeta {
    readonly env: ImportMetaEnv
}
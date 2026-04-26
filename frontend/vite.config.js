import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

/**
 * Vite-Konfiguration für das React-Frontend.
 *
 * Vite ist der moderne Build-Tool und Development-Server für React-Anwendungen.
 * Hier wird der Proxy zum Spring-Backend konfiguriert, damit API-Aufrufe
 * während der Entwicklung ohne CORS-Probleme funktionieren.
 *
 * Good Practice: Der Proxy leitet alle /api-Anfragen automatisch an das Backend weiter.
 * Dadurch kann das Frontend während der Entwicklung nahtlos mit dem Spring-Backend kommunizieren.
 *
 * Für Anfänger: Beim Start mit `npm run dev` läuft Vite auf Port 5173 und leitet
 * API-Aufrufe automatisch an http://localhost:8080 weiter.
 */
export default defineConfig({
    plugins: [react()],
    server: {
        port: 5173,
        proxy: {
            '/api': {
                target: 'http://localhost:8080',
                changeOrigin: true,
                secure: false
            }
        }
    },
    build: {
        outDir: 'dist',
        emptyOutDir: true
    }
})
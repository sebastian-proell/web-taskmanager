import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.tsx'
import './index.css'

/**
 * Einstiegspunkt der React-Anwendung.
 *
 * Diese Datei initialisiert React und rendert die Root-Komponente
 * in das HTML-Element mit der ID "root".
 *
 * Good Practice: React.StrictMode aktiviert zusätzliche Prüfungen
 * in der Entwicklung, um potenzielle Probleme frühzeitig zu erkennen.
 *
 * Für Anfänger: Dies ist der erste Code, der beim Start der Anwendung
 * ausgeführt wird. Er verbindet React mit dem DOM.
 */
ReactDOM.createRoot(document.getElementById('root')!).render(
    <React.StrictMode>
        <App />
    </React.StrictMode>
)
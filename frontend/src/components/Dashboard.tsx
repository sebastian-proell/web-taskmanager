/**
 * Dashboard-View.
 *
 * Einfache Übersichtsseite als Startpunkt der Anwendung.
 * Zeigt eine Willkommensnachricht und Hinweise zur Navigation.
 *
 * Good Practice: Eine separate Dashboard-Komponente dient als Einstiegspunkt
 * und kann später mit Statistiken oder Übersichten erweitert werden.
 *
 * Wichtig zu wissen: Diese Komponente wird angezeigt, wenn der Benutzer
 * auf der Startseite ist. Sie enthält nur statischen Inhalt.
 */
export default function Dashboard() {
    return (
        <div>
            <h2>Willkommen im TaskManager</h2>
            <p>Dies ist die React-Variante der komponentenbasierten Web-Benutzerschnittstelle (Termin 4).</p>
            <p>Navigieren Sie über das Menü zu den Aufgaben.</p>
        </div>
    )
}
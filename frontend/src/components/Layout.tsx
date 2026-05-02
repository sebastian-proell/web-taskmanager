import { Link, useLocation } from 'react-router-dom'

/**
 * Layout-Komponente mit Navigation.
 *
 * Diese Komponente stellt die gemeinsame Navigation (Dashboard / Aufgaben)
 * für alle Views bereit und umschließt den Inhalt.
 *
 * Good Practice: Ein zentrales Layout sorgt für konsistentes Design
 * und erleichtert die spätere Erweiterung der Anwendung.
 *
 * Wichtig zu wissen: Die Navigation wird mit React Router Links realisiert.
 * Der aktive Link wird automatisch hervorgehoben.
 */
export default function Layout({ children }: { children: React.ReactNode }) {
    const location = useLocation()

    return (
        <div className="layout">
            <header className="header">
                <h1>TaskManager</h1>
                <nav>
                    <Link
                        to="/"
                        className={location.pathname === '/' ? 'active' : ''}
                    >
                        Dashboard
                    </Link>
                    <Link
                        to="/tasks"
                        className={location.pathname === '/tasks' ? 'active' : ''}
                    >
                        Aufgaben verwalten
                    </Link>
                </nav>
            </header>
            <main className="main-content">
                {children}
            </main>
        </div>
    )
}
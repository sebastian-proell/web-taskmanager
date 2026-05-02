import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import Layout from './components/Layout.tsx'
import Dashboard from './components/Dashboard.tsx'
import TaskList from './components/TaskList.tsx'

/**
 * Hauptkomponente der React-Anwendung.
 *
 * Hier wird das Routing und das gemeinsame Layout definiert.
 * Alle Views werden über das Layout gerendert.
 *
 * Good Practice: React Router ermöglicht deklarative Navigation
 * und ein zentrales Layout für ein einheitliches Erscheinungsbild.
 *
 * Wichtig zu wissen: Die App-Komponente ist der zentrale Container.
 * Alle anderen Komponenten werden über das Routing hier eingebunden.
 */
function App() {
    return (
        <Router>
            <Layout>
                <Routes>
                    <Route path="/" element={<Dashboard />} />
                    <Route path="/tasks" element={<TaskList />} />
                </Routes>
            </Layout>
        </Router>
    )
}

export default App
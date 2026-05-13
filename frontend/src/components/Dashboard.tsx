import QuickStats from './QuickStats';
import {
    Container,
    Typography,
    Button,
    Box,
    Paper
} from '@mui/material';
import { useNavigate } from 'react-router-dom';

/**
 * Dashboard – Reine Übersichtsseite.
 *
 * Zeigt Statistiken und eine kurze Begrüßung.
 * Die eigentliche Bearbeitung von Tasks findet auf der Seite "Aufgaben verwalten" statt.
 *
 * Best Practice:
 * - Dashboard sollte primär informativ sein (Quick Overview)
 * - Handlungsaufforderungen (z.B. Button) führen zur eigentlichen Arbeitsseite
 * - Keine doppelte Darstellung von Formularen
 *
 * Wichtig zu wissen:
 * Eine klare Trennung zwischen Übersicht (Dashboard) und Bearbeitung (Management)
 * verbessert die Usability deutlich. Nutzer:innen verstehen sofort, wo sie
 * nur einen schnellen Überblick bekommen und wo sie aktiv arbeiten können.
 */
export default function Dashboard() {
    const navigate = useNavigate();

    // Beispielwerte (später dynamisch)
    const stats = {
        open: 7,
        inProgress: 4,
        completed: 12,
        blocked: 2,
    };

    return (
        <Container maxWidth="xl" sx={{ py: 4 }}>
            <Box sx={{ mb: 4 }}>
                <Typography variant="h4" component="h1" gutterBottom>
                    Willkommen zurück!
                </Typography>
                <Typography variant="body1" color="text.secondary" sx={{ maxWidth: 700 }}>
                    Hier siehst du auf einen Blick, wie es um deine Aufgaben steht.
                </Typography>
            </Box>

            <QuickStats
                open={stats.open}
                inProgress={stats.inProgress}
                completed={stats.completed}
                blocked={stats.blocked}
            />

            <Paper elevation={1} sx={{ p: 4, mt: 4, textAlign: 'center' }}>
                <Typography variant="h6" gutterBottom>
                    Tasks bearbeiten oder neue anlegen?
                </Typography>
                <Typography variant="body2" color="text.secondary" sx={{ mb: 3 }}>
                    Wechsle zur Aufgabenverwaltung, um Tasks zu erstellen, zu bearbeiten oder zu löschen.
                </Typography>
                <Button
                    variant="contained"
                    size="large"
                    onClick={() => navigate('/tasks')}
                >
                    Zu den Aufgaben wechseln
                </Button>
            </Paper>
        </Container>
    );
}
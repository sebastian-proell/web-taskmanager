import QuickStats from './QuickStats';
import TaskList from './TaskList';
import { useTasks } from '../hooks/useTasks';
import {
    Container,
    Typography,
    Button,
    Box,
    Paper,
    Divider
} from '@mui/material';
import { useNavigate } from 'react-router-dom';

/**
 * Dashboard – Übersichtsseite mit echten Daten.
 *
 * Zeigt dynamische Statistiken basierend auf den echten Tasks aus dem Backend
 * und integriert direkt die TaskList für einen schnellen Überblick.
 */
export default function Dashboard() {
    const navigate = useNavigate();
    const { tasks, loading, error, startEditing, deleteTask } = useTasks();

    // Dynamische Berechnung der Statistiken aus den echten Daten
    const stats = {
        open: tasks.filter(t => t.status === 'OPEN').length,
        inProgress: tasks.filter(t => t.status === 'IN_PROGRESS').length,
        completed: tasks.filter(t => t.status === 'COMPLETED').length,
        blocked: tasks.filter(t => t.status === 'BLOCKED').length,
    };

    if (loading) {
        return (
            <Container maxWidth="xl" sx={{ py: 4 }}>
                <Typography>Lade Daten...</Typography>
            </Container>
        );
    }

    if (error) {
        return (
            <Container maxWidth="xl" sx={{ py: 4 }}>
                <Typography color="error">Fehler beim Laden der Tasks: {error}</Typography>
            </Container>
        );
    }

    return (
        <Container maxWidth="xl" sx={{ py: 4 }}>
            <Box sx={{ mb: 4 }}>
                <Typography variant="h4" component="h1" gutterBottom>
                    Willkommen zurück!
                </Typography>
                <Typography variant="body1" color="text.secondary">
                    Hier siehst du einen schnellen Überblick über deine aktuellen Aufgaben.
                </Typography>
            </Box>

            {/* Dynamische Statistiken aus dem Backend */}
            <QuickStats
                open={stats.open}
                inProgress={stats.inProgress}
                completed={stats.completed}
                blocked={stats.blocked}
            />

            <Divider sx={{ my: 4 }} />

            {/* Task Liste direkt im Dashboard */}
            <Paper elevation={2} sx={{ p: 3, mb: 4 }}>
                <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 2 }}>
                    <Typography variant="h6">
                        Aktuelle Tasks
                    </Typography>
                    <Button
                        variant="outlined"
                        onClick={() => navigate('/tasks')}
                    >
                        Alle Tasks verwalten
                    </Button>
                </Box>
                <TaskList
                    tasks={tasks}
                    loading={loading}
                    error={error}
                    onEdit={startEditing}
                    onDelete={deleteTask}
                />
            </Paper>

            {/* Optionaler Hinweis */}
            <Box sx={{ textAlign: 'center', mt: 4 }}>
                <Typography variant="body2" color="text.secondary">
                    Für die vollständige Bearbeitung von Tasks bitte in den Bereich „Aufgaben verwalten“ wechseln.
                </Typography>
            </Box>
        </Container>
    );
}
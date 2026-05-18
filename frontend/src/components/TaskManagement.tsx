import TaskForm from './TaskForm';
import TaskList from './TaskList';
import { useTasks } from '../hooks/useTasks';
import { Container, Grid, Typography, Paper, Divider, Button } from '@mui/material';

/**
 * TaskManagement – Seite zur Verwaltung von Tasks.
 *
 * Diese Komponente verbindet TaskForm (links) und TaskList (rechts)
 * in einem responsiven Layout. Beim Klick auf "Bearbeiten" in der Liste
 * wird der Task automatisch in das Formular geladen.
 *
 * Best Practice:
 * - Zentrale State-Verwaltung über useTasks Hook
 * - Klare Trennung von Präsentation (Form + Liste) und Logik (Hook)
 * - Responsives Design mit MUI Grid (nebeneinander → untereinander)
 *
 * Wichtig zu wissen:
 * Da TaskList und TaskForm beide denselben useTasks-Hook verwenden
 * (wenn sie in derselben Komponenten-Hierarchie gerendert werden),
 * wird der editingTask-State korrekt geteilt. Der Klick auf "Bearbeiten"
 * in der Liste setzt editingTask, und TaskForm reagiert darauf.
 */
export default function TaskManagement() {
    const {
        tasks,
        loading,
        error,
        editingTask,
        startEditing,
        cancelEditing,
        refreshAfterSave,
        deleteTask
    } = useTasks();

    const handleTaskSaved = async () => {
        await refreshAfterSave();
        cancelEditing();
    };

    return (
        <Container maxWidth="xl" sx={{ py: 4 }}>
            <Typography variant="h4" gutterBottom>
                Aufgaben verwalten
            </Typography>

            <Typography variant="body1" color="text.secondary" sx={{ mb: 3 }}>
                Lege neue Tasks an oder bearbeite bestehende Aufgaben.
            </Typography>

            <Divider sx={{ mb: 4 }} />

            <Grid container spacing={4}>

                {/* Formular (links) */}
                <Grid item xs={12} md={5} lg={4}>
                    <Paper elevation={2} sx={{ p: 3 }}>
                        <Typography variant="h6" gutterBottom>
                            {editingTask ? 'Task bearbeiten' : 'Neuen Task erstellen'}
                        </Typography>

                        <TaskForm
                            task={editingTask}
                            onSaved={handleTaskSaved}
                        />

                        {editingTask && (
                            <Button
                                variant="text"
                                onClick={cancelEditing}
                                sx={{ mt: 2 }}
                            >
                                Bearbeitung abbrechen
                            </Button>
                        )}
                    </Paper>
                </Grid>

                {/* Liste (rechts) */}
                <Grid item xs={12} md={7} lg={8}>
                    <Paper elevation={2} sx={{ p: 3 }}>
                        <Typography variant="h6" gutterBottom>
                            Alle Tasks
                        </Typography>
                        <TaskList
                            tasks={tasks}
                            loading={loading}
                            error={error}
                            onEdit={startEditing}
                            onDelete={deleteTask}
                        />
                    </Paper>
                </Grid>

            </Grid>
        </Container>
    );
}
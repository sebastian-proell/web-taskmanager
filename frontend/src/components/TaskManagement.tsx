import TaskForm from './TaskForm';
import TaskList from './TaskList';
import { useTasks } from '../hooks/useTasks';
import { Container, Grid, Typography, Paper, Divider, Button } from '@mui/material';

/**
 * TaskManagement – Seite zur Verwaltung von Tasks.
 *
 * Formular und Liste sind klar getrennt.
 * Beim Klick auf "Bearbeiten" in der Liste wird der Task automatisch ins Formular geladen.
 */
export default function TaskManagement() {
    const {
        editingTask,
        cancelEditing,
        refreshAfterSave
    } = useTasks();

    const handleTaskSaved = () => {
        refreshAfterSave();
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
                        <TaskList />
                    </Paper>
                </Grid>

            </Grid>
        </Container>
    );
}
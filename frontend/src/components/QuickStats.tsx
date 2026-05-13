import { Grid, Paper, Typography, Box } from '@mui/material';
import AssignmentIcon from '@mui/icons-material/Assignment';
import PlayCircleOutlineIcon from '@mui/icons-material/PlayCircleOutline';
import CheckCircleOutlineIcon from '@mui/icons-material/CheckCircleOutline';
import BlockIcon from '@mui/icons-material/Block';

/**
 * QuickStats – Zeigt schnelle Übersichtszahlen zu den Tasks an.
 *
 * Diese Komponente demonstriert den Einsatz von MUI Cards in Kombination
 * mit Icons und responsive Grid-Layouts.
 *
 * Best Practice:
 * - Kleine, fokussierte Komponenten für Dashboard-Statistiken sind sehr wartbar
 * - Icons aus @mui/icons-material sorgen für visuelle Klarheit und bessere UX
 * - Jede Statistik-Card ist eigenständig und leicht erweiterbar
 * - Farbliche Akzente (primary, success, warning, error) erhöhen die Lesbarkeit
 *
 * Wichtig zu wissen:
 * Komponentenbibliotheken wie MUI liefern nicht nur einzelne Buttons oder Inputs,
 * sondern ein ganzes Ökosystem aus Layout-, Feedback- und Datenvisualisierungs-Komponenten.
 * Durch die Kombination von Paper, Grid und Icons entsteht mit wenig Aufwand
 * ein professionelles und ansprechendes Dashboard-Erlebnis.
 * Solche Statistik-Komponenten sind in realen Business-Anwendungen sehr häufig
 * und zeigen gut, wie man mit MUI schnell Mehrwert schaffen kann.
 */
interface QuickStatsProps {
    open: number;
    inProgress: number;
    completed: number;
    blocked: number;
}

export default function QuickStats({ open, inProgress, completed, blocked }: QuickStatsProps) {
    const stats = [
        {
            label: 'Offen',
            value: open,
            icon: <AssignmentIcon sx={{ fontSize: 40 }} />,
            color: 'primary.main',
        },
        {
            label: 'In Bearbeitung',
            value: inProgress,
            icon: <PlayCircleOutlineIcon sx={{ fontSize: 40 }} />,
            color: 'info.main',
        },
        {
            label: 'Abgeschlossen',
            value: completed,
            icon: <CheckCircleOutlineIcon sx={{ fontSize: 40 }} />,
            color: 'success.main',
        },
        {
            label: 'Blockiert',
            value: blocked,
            icon: <BlockIcon sx={{ fontSize: 40 }} />,
            color: 'error.main',
        },
    ];

    return (
        <Grid container spacing={3} sx={{ mb: 4 }}>
            {stats.map((stat, index) => (
                <Grid item xs={6} sm={3} key={index}>
                    <Paper
                        elevation={2}
                        sx={{
                            p: 3,
                            display: 'flex',
                            alignItems: 'center',
                            gap: 2,
                            transition: 'transform 0.2s, box-shadow 0.2s',
                            '&:hover': {
                                transform: 'translateY(-4px)',
                                boxShadow: 4,
                            }
                        }}
                    >
                        <Box sx={{ color: stat.color }}>
                            {stat.icon}
                        </Box>
                        <Box>
                            <Typography variant="h4" component="div" fontWeight="bold">
                                {stat.value}
                            </Typography>
                            <Typography variant="body2" color="text.secondary">
                                {stat.label}
                            </Typography>
                        </Box>
                    </Paper>
                </Grid>
            ))}
        </Grid>
    );
}
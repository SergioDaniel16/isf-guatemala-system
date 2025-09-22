import React from 'react';
import { ThemeProvider, createTheme } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import { AppBar, Toolbar, Typography, Container } from '@mui/material';
import TestConnection from './components/TestConnection';

// Crear tema con los colores de ISF Guatemala
const theme = createTheme({
  palette: {
    primary: {
      main: '#1976d2', // Azul principal
    },
    secondary: {
      main: '#2196f3', // Azul secundario
    },
    background: {
      default: '#f5f5f5', // Gris claro
    },
  },
});

function App() {
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <AppBar position="static">
        <Toolbar>
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            Sistema ISF Guatemala - Prueba de Conexión
          </Typography>
        </Toolbar>
      </AppBar>
      
      <Container maxWidth="lg">
        <TestConnection />
      </Container>
    </ThemeProvider>
  );
}

export default App;
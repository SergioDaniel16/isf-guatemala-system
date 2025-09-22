import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Button, Card, CardContent, Typography, Box } from '@mui/material';

const TestConnection = () => {
  const [backendStatus, setBackendStatus] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const testBackendConnection = async () => {
    setLoading(true);
    setError(null);
    
    try {
      const response = await axios.get('http://localhost:8080/api/test');
      setBackendStatus(response.data);
    } catch (err) {
      setError('Error conectando con el backend: ' + err.message);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    testBackendConnection();
  }, []);

  return (
    <Box sx={{ padding: 3 }}>
      <Card>
        <CardContent>
          <Typography variant="h5" gutterBottom>
            Test de Conexión Backend
          </Typography>
          
          <Button 
            variant="contained" 
            onClick={testBackendConnection}
            disabled={loading}
            sx={{ mb: 2 }}
          >
            {loading ? 'Probando...' : 'Probar Conexión'}
          </Button>

          {error && (
            <Typography color="error" sx={{ mt: 2 }}>
              {error}
            </Typography>
          )}

          {backendStatus && (
            <Box sx={{ mt: 2 }}>
              <Typography variant="h6" color="success.main">
                ✅ Backend conectado correctamente
              </Typography>
              <Typography variant="body2" sx={{ mt: 1 }}>
                Mensaje: {backendStatus.message}
              </Typography>
              <Typography variant="body2">
                Status: {backendStatus.status}
              </Typography>
              <Typography variant="body2">
                Timestamp: {new Date(backendStatus.timestamp).toLocaleString()}
              </Typography>
            </Box>
          )}
        </CardContent>
      </Card>
    </Box>
  );
};

export default TestConnection;

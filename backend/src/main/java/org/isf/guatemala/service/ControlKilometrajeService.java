package org.isf.guatemala.service;

import org.isf.guatemala.model.ControlKilometraje;
import org.isf.guatemala.model.Vehiculo;
import org.isf.guatemala.repository.ControlKilometrajeRepository;
import org.isf.guatemala.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ControlKilometrajeService {
    
    @Autowired
    private ControlKilometrajeRepository controlKilometrajeRepository;
    
    @Autowired
    private VehiculoRepository vehiculoRepository;
    
    private static final BigDecimal FACTOR_MILLAS_A_KM = new BigDecimal("1.609");
    private static final BigDecimal KM_BASE = new BigDecimal("50");
    
    public List<ControlKilometraje> obtenerTodos() {
        return controlKilometrajeRepository.findAll();
    }
    
    public Optional<ControlKilometraje> obtenerPorId(Long id) {
        return controlKilometrajeRepository.findById(id);
    }
    
    public List<ControlKilometraje> obtenerViajesActivos() {
        return controlKilometrajeRepository.findByEstado(ControlKilometraje.EstadoViaje.ACTIVO);
    }
    
    // Iniciar un viaje (abrir viaje)
    public ControlKilometraje iniciarViaje(ControlKilometraje controlKilometraje) {
        Vehiculo vehiculo = vehiculoRepository.findById(controlKilometraje.getVehiculo().getId())
            .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));
        
        // Establecer millaje de salida desde el odómetro actual del vehículo
        controlKilometraje.setMillajeSalida(vehiculo.getOdometroActual());
        controlKilometraje.setEstado(ControlKilometraje.EstadoViaje.ACTIVO);
        controlKilometraje.setFechaInicio(LocalDateTime.now());
        
        return controlKilometrajeRepository.save(controlKilometraje);
    }
    
    // Cerrar un viaje
    public ControlKilometraje cerrarViaje(Long id, BigDecimal millajeEntrada, String fotoFinal) {
        ControlKilometraje control = controlKilometrajeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Viaje no encontrado"));
        
        if (control.getEstado() == ControlKilometraje.EstadoViaje.CERRADO) {
            throw new RuntimeException("El viaje ya está cerrado");
        }
        
        Vehiculo vehiculo = control.getVehiculo();
        
        // Establecer millaje de entrada
        control.setMillajeEntrada(millajeEntrada);
        control.setFotoFinal(fotoFinal);
        
        // Calcular millas recorridas
        BigDecimal millasRecorrido = millajeEntrada.subtract(control.getMillajeSalida());
        control.setMillasRecorrido(millasRecorrido);
        
        // Conversión automática a kilómetros si el vehículo está en millas
        BigDecimal km;
        if (vehiculo.getUnidadMedida() == Vehiculo.UnidadMedida.MILLAS) {
            km = millasRecorrido.multiply(FACTOR_MILLAS_A_KM)
                .setScale(2, RoundingMode.HALF_UP);
        } else {
            km = millasRecorrido;
        }
        control.setKm(km);
        
        // Calcular KM Extra si supera 50 km
        if (km.compareTo(KM_BASE) > 0) {
            BigDecimal kmExtra = km.subtract(KM_BASE);
            control.setKmExtra(kmExtra);
        } else {
            control.setKmExtra(BigDecimal.ZERO);
        }
        
        // Actualizar odómetro del vehículo
        vehiculo.setOdometroActual(millajeEntrada);
        vehiculoRepository.save(vehiculo);
        
        // Cerrar viaje
        control.setEstado(ControlKilometraje.EstadoViaje.CERRADO);
        control.setFechaFin(LocalDateTime.now());
        
        return controlKilometrajeRepository.save(control);
    }
    
    public ControlKilometraje actualizar(Long id, ControlKilometraje controlKilometraje) {
        if (!controlKilometrajeRepository.existsById(id)) {
            throw new RuntimeException("Control de kilometraje no encontrado");
        }
        controlKilometraje.setId(id);
        return controlKilometrajeRepository.save(controlKilometraje);
    }
    
    public void eliminar(Long id) {
        controlKilometrajeRepository.deleteById(id);
    }
    
    // Métodos de filtrado para reportes
    public List<ControlKilometraje> obtenerPorProyecto(Long proyectoId) {
        return controlKilometrajeRepository.findByProyectoId(proyectoId);
    }
    
    public List<ControlKilometraje> obtenerPorVehiculo(Long vehiculoId) {
        return controlKilometrajeRepository.findByVehiculoId(vehiculoId);
    }
    
    public List<ControlKilometraje> obtenerPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        return controlKilometrajeRepository.findByFechaBetween(fechaInicio, fechaFin);
    }
}

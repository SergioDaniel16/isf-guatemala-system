package org.isf.guatemala.service;

import org.isf.guatemala.dto.request.ControlKilometrajeRequestDTO;
import org.isf.guatemala.dto.response.ControlKilometrajeResponseDTO;
import org.isf.guatemala.exception.BusinessRuleException;
import org.isf.guatemala.exception.ResourceNotFoundException;
import org.isf.guatemala.mapper.EntityMapper;
import org.isf.guatemala.model.ControlKilometraje;
import org.isf.guatemala.model.Vehiculo;
import org.isf.guatemala.repository.ControlKilometrajeRepository;
import org.isf.guatemala.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ControlKilometrajeService {
    
    @Autowired
    private ControlKilometrajeRepository controlKilometrajeRepository;
    
    @Autowired
    private VehiculoRepository vehiculoRepository;
    
    @Autowired
    private EntityMapper mapper;
    
    private static final BigDecimal FACTOR_MILLAS_A_KM = new BigDecimal("1.609");
    private static final BigDecimal KM_BASE = new BigDecimal("50");
    
    public List<ControlKilometrajeResponseDTO> obtenerTodos() {
        List<ControlKilometraje> controles = controlKilometrajeRepository.findAll();
        return mapper.toControlKilometrajeResponseList(controles);
    }
    
    public ControlKilometrajeResponseDTO obtenerPorId(Long id) {
        ControlKilometraje control = controlKilometrajeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Control de Kilometraje", "id", id));
        return mapper.toControlKilometrajeResponse(control);
    }
    
    public List<ControlKilometrajeResponseDTO> obtenerViajesActivos() {
        List<ControlKilometraje> controles = controlKilometrajeRepository
            .findByEstado(ControlKilometraje.EstadoViaje.ACTIVO);
        return mapper.toControlKilometrajeResponseList(controles);
    }
    
    // Iniciar un viaje (abrir viaje)
    public ControlKilometrajeResponseDTO iniciarViaje(ControlKilometrajeRequestDTO dto) {
        Vehiculo vehiculo = vehiculoRepository.findById(dto.getVehiculoId())
            .orElseThrow(() -> new ResourceNotFoundException("Vehículo", "id", dto.getVehiculoId()));
        
        ControlKilometraje control = mapper.toControlKilometrajeEntity(dto);
        
        // Establecer millaje de salida desde el odómetro actual del vehículo
        control.setMillajeSalida(vehiculo.getOdometroActual());
        control.setEstado(ControlKilometraje.EstadoViaje.ACTIVO);
        control.setFechaInicio(LocalDateTime.now());
        
        ControlKilometraje controlGuardado = controlKilometrajeRepository.save(control);
        return mapper.toControlKilometrajeResponse(controlGuardado);
    }
    
    // Cerrar un viaje
    public ControlKilometrajeResponseDTO cerrarViaje(Long id, BigDecimal millajeEntrada, String fotoFinal) {
        ControlKilometraje control = controlKilometrajeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Control de Kilometraje", "id", id));
        
        if (control.getEstado() == ControlKilometraje.EstadoViaje.CERRADO) {
            throw new BusinessRuleException("El viaje ya está cerrado");
        }
        
        Vehiculo vehiculo = control.getVehiculo();
        
        // Validar que el millaje de entrada sea mayor al de salida
        if (millajeEntrada.compareTo(control.getMillajeSalida()) <= 0) {
            throw new BusinessRuleException("El millaje de entrada debe ser mayor al millaje de salida");
        }
        
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
        
        ControlKilometraje controlActualizado = controlKilometrajeRepository.save(control);
        return mapper.toControlKilometrajeResponse(controlActualizado);
    }
    
    public ControlKilometrajeResponseDTO actualizar(Long id, ControlKilometrajeRequestDTO dto) {
        if (!controlKilometrajeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Control de Kilometraje", "id", id);
        }
        
        ControlKilometraje control = mapper.toControlKilometrajeEntity(dto);
        control.setId(id);
        
        ControlKilometraje controlActualizado = controlKilometrajeRepository.save(control);
        return mapper.toControlKilometrajeResponse(controlActualizado);
    }
    
    public void eliminar(Long id) {
        if (!controlKilometrajeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Control de Kilometraje", "id", id);
        }
        controlKilometrajeRepository.deleteById(id);
    }
    
    // Métodos de filtrado para reportes
    public List<ControlKilometrajeResponseDTO> obtenerPorProyecto(Long proyectoId) {
        List<ControlKilometraje> controles = controlKilometrajeRepository.findByProyectoId(proyectoId);
        return mapper.toControlKilometrajeResponseList(controles);
    }
    
    public List<ControlKilometrajeResponseDTO> obtenerPorVehiculo(Long vehiculoId) {
        List<ControlKilometraje> controles = controlKilometrajeRepository.findByVehiculoId(vehiculoId);
        return mapper.toControlKilometrajeResponseList(controles);
    }
    
    public List<ControlKilometrajeResponseDTO> obtenerPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        List<ControlKilometraje> controles = controlKilometrajeRepository
            .findByFechaBetween(fechaInicio, fechaFin);
        return mapper.toControlKilometrajeResponseList(controles);
    }
}

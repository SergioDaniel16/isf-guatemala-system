package org.isf.guatemala.service;

import org.isf.guatemala.model.RegistroHoras;
import org.isf.guatemala.model.Empleado;
import org.isf.guatemala.model.Tarea;
import org.isf.guatemala.repository.RegistroHorasRepository;
import org.isf.guatemala.repository.EmpleadoRepository;
import org.isf.guatemala.repository.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RegistroHorasService {
    
    @Autowired
    private RegistroHorasRepository registroHorasRepository;
    
    @Autowired
    private EmpleadoRepository empleadoRepository;
    
    @Autowired
    private TareaRepository tareaRepository;
    
    public List<RegistroHoras> obtenerTodos() {
        return registroHorasRepository.findAll();
    }
    
    public Optional<RegistroHoras> obtenerPorId(Long id) {
        return registroHorasRepository.findById(id);
    }
    
    public RegistroHoras crear(RegistroHoras registroHoras) {
        // Obtener la tarea para determinar si es cobrable
        Tarea tarea = tareaRepository.findById(registroHoras.getTarea().getId())
            .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
        
        // Copiar el atributo esCobrable de la tarea
        registroHoras.setEsCobrable(tarea.getEsCobrable());
        
        // Calcular horas totales: horas + (minutos / 60)
        BigDecimal horasTotales = BigDecimal.valueOf(registroHoras.getHoras())
            .add(BigDecimal.valueOf(registroHoras.getMinutos())
                .divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP));
        registroHoras.setHorasTotales(horasTotales);
        
        // Calcular costo total solo si es cobrable
        if (registroHoras.getEsCobrable()) {
            Empleado empleado = empleadoRepository.findById(registroHoras.getEmpleado().getId())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
            
            BigDecimal costoTotal = horasTotales.multiply(empleado.getPuesto().getSalarioPorHora());
            registroHoras.setCostoTotal(costoTotal);
        } else {
            registroHoras.setCostoTotal(BigDecimal.ZERO);
        }
        
        return registroHorasRepository.save(registroHoras);
    }
    
    public RegistroHoras actualizar(Long id, RegistroHoras registroHoras) {
        if (!registroHorasRepository.existsById(id)) {
            throw new RuntimeException("Registro de horas no encontrado");
        }
        registroHoras.setId(id);
        return crear(registroHoras); // Reutiliza la lógica de cálculo
    }
    
    public void eliminar(Long id) {
        registroHorasRepository.deleteById(id);
    }
    
    // Métodos de filtrado para reportes
    public List<RegistroHoras> obtenerPorProyecto(Long proyectoId) {
        return registroHorasRepository.findByProyectoId(proyectoId);
    }
    
    public List<RegistroHoras> obtenerPorEmpleado(Long empleadoId) {
        return registroHorasRepository.findByEmpleadoId(empleadoId);
    }
    
    public List<RegistroHoras> obtenerPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        return registroHorasRepository.findByFechaBetween(fechaInicio, fechaFin);
    }
    
    public List<RegistroHoras> obtenerConFiltros(
        Long proyectoId, 
        Long empleadoId, 
        Boolean esCobrable,
        LocalDate fechaInicio, 
        LocalDate fechaFin
    ) {
        return registroHorasRepository.findByFiltros(
            proyectoId, empleadoId, esCobrable, fechaInicio, fechaFin);
    }
}

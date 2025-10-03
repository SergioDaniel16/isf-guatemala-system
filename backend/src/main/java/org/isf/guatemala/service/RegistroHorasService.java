package org.isf.guatemala.service;

import org.isf.guatemala.dto.request.RegistroHorasRequestDTO;
import org.isf.guatemala.dto.response.RegistroHorasResponseDTO;
import org.isf.guatemala.exception.ResourceNotFoundException;
import org.isf.guatemala.mapper.EntityMapper;
import org.isf.guatemala.model.Empleado;
import org.isf.guatemala.model.RegistroHoras;
import org.isf.guatemala.model.Tarea;
import org.isf.guatemala.repository.EmpleadoRepository;
import org.isf.guatemala.repository.RegistroHorasRepository;
import org.isf.guatemala.repository.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class RegistroHorasService {
    
    @Autowired
    private RegistroHorasRepository registroHorasRepository;
    
    @Autowired
    private EmpleadoRepository empleadoRepository;
    
    @Autowired
    private TareaRepository tareaRepository;
    
    @Autowired
    private EntityMapper mapper;
    
    public List<RegistroHorasResponseDTO> obtenerTodos() {
        List<RegistroHoras> registros = registroHorasRepository.findAll();
        return mapper.toRegistroHorasResponseList(registros);
    }
    
    public RegistroHorasResponseDTO obtenerPorId(Long id) {
        RegistroHoras registroHoras = registroHorasRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Registro de Horas", "id", id));
        return mapper.toRegistroHorasResponse(registroHoras);
    }
    
    public RegistroHorasResponseDTO crear(RegistroHorasRequestDTO dto) {
        RegistroHoras registroHoras = mapper.toRegistroHorasEntity(dto);
        
        // Obtener la tarea para determinar si es cobrable
        Tarea tarea = tareaRepository.findById(dto.getTareaId())
            .orElseThrow(() -> new ResourceNotFoundException("Tarea", "id", dto.getTareaId()));
        
        // Copiar el atributo esCobrable de la tarea
        registroHoras.setEsCobrable(tarea.getEsCobrable());
        
        // Calcular horas totales: horas + (minutos / 60)
        BigDecimal horasTotales = BigDecimal.valueOf(dto.getHoras())
            .add(BigDecimal.valueOf(dto.getMinutos())
                .divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP));
        registroHoras.setHorasTotales(horasTotales);
        
        // Calcular costo total solo si es cobrable
        if (registroHoras.getEsCobrable()) {
            Empleado empleado = empleadoRepository.findById(dto.getEmpleadoId())
                .orElseThrow(() -> new ResourceNotFoundException("Empleado", "id", dto.getEmpleadoId()));
            
            BigDecimal costoTotal = horasTotales.multiply(empleado.getPuesto().getSalarioPorHora());
            registroHoras.setCostoTotal(costoTotal);
        } else {
            registroHoras.setCostoTotal(BigDecimal.ZERO);
        }
        
        RegistroHoras registroGuardado = registroHorasRepository.save(registroHoras);
        return mapper.toRegistroHorasResponse(registroGuardado);
    }
    
    public RegistroHorasResponseDTO actualizar(Long id, RegistroHorasRequestDTO dto) {
        if (!registroHorasRepository.existsById(id)) {
            throw new ResourceNotFoundException("Registro de Horas", "id", id);
        }
        
        // Reutilizar la lógica de creación
        RegistroHoras registroHoras = mapper.toRegistroHorasEntity(dto);
        registroHoras.setId(id);
        
        // Obtener la tarea para determinar si es cobrable
        Tarea tarea = tareaRepository.findById(dto.getTareaId())
            .orElseThrow(() -> new ResourceNotFoundException("Tarea", "id", dto.getTareaId()));
        
        registroHoras.setEsCobrable(tarea.getEsCobrable());
        
        // Calcular horas totales
        BigDecimal horasTotales = BigDecimal.valueOf(dto.getHoras())
            .add(BigDecimal.valueOf(dto.getMinutos())
                .divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP));
        registroHoras.setHorasTotales(horasTotales);
        
        // Calcular costo total
        if (registroHoras.getEsCobrable()) {
            Empleado empleado = empleadoRepository.findById(dto.getEmpleadoId())
                .orElseThrow(() -> new ResourceNotFoundException("Empleado", "id", dto.getEmpleadoId()));
            
            BigDecimal costoTotal = horasTotales.multiply(empleado.getPuesto().getSalarioPorHora());
            registroHoras.setCostoTotal(costoTotal);
        } else {
            registroHoras.setCostoTotal(BigDecimal.ZERO);
        }
        
        RegistroHoras registroActualizado = registroHorasRepository.save(registroHoras);
        return mapper.toRegistroHorasResponse(registroActualizado);
    }
    
    public void eliminar(Long id) {
        if (!registroHorasRepository.existsById(id)) {
            throw new ResourceNotFoundException("Registro de Horas", "id", id);
        }
        registroHorasRepository.deleteById(id);
    }
    
    // Métodos de filtrado para reportes
    public List<RegistroHorasResponseDTO> obtenerPorProyecto(Long proyectoId) {
        List<RegistroHoras> registros = registroHorasRepository.findByProyectoId(proyectoId);
        return mapper.toRegistroHorasResponseList(registros);
    }
    
    public List<RegistroHorasResponseDTO> obtenerPorEmpleado(Long empleadoId) {
        List<RegistroHoras> registros = registroHorasRepository.findByEmpleadoId(empleadoId);
        return mapper.toRegistroHorasResponseList(registros);
    }
    
    public List<RegistroHorasResponseDTO> obtenerPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        List<RegistroHoras> registros = registroHorasRepository.findByFechaBetween(fechaInicio, fechaFin);
        return mapper.toRegistroHorasResponseList(registros);
    }
    
    public List<RegistroHorasResponseDTO> obtenerConFiltros(
        Long proyectoId, 
        Long empleadoId, 
        Boolean esCobrable,
        LocalDate fechaInicio, 
        LocalDate fechaFin
    ) {
        List<RegistroHoras> registros = registroHorasRepository.findByFiltros(
            proyectoId, empleadoId, esCobrable, fechaInicio, fechaFin);
        return mapper.toRegistroHorasResponseList(registros);
    }
}

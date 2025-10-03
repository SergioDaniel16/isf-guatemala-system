package org.isf.guatemala.service;

import org.isf.guatemala.dto.request.EmpleadoRequestDTO;
import org.isf.guatemala.dto.response.EmpleadoResponseDTO;
import org.isf.guatemala.exception.ResourceNotFoundException;
import org.isf.guatemala.mapper.EntityMapper;
import org.isf.guatemala.model.Empleado;
import org.isf.guatemala.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class EmpleadoService {
    
    @Autowired
    private EmpleadoRepository empleadoRepository;
    
    @Autowired
    private EntityMapper mapper;
    
    public List<EmpleadoResponseDTO> obtenerTodos() {
        List<Empleado> empleados = empleadoRepository.findAll();
        return mapper.toEmpleadoResponseList(empleados);
    }
    
    public EmpleadoResponseDTO obtenerPorId(Long id) {
        Empleado empleado = empleadoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Empleado", "id", id));
        return mapper.toEmpleadoResponse(empleado);
    }
    
    public EmpleadoResponseDTO crear(EmpleadoRequestDTO dto) {
        Empleado empleado = mapper.toEmpleadoEntity(dto);
        Empleado empleadoGuardado = empleadoRepository.save(empleado);
        return mapper.toEmpleadoResponse(empleadoGuardado);
    }
    
    public EmpleadoResponseDTO actualizar(Long id, EmpleadoRequestDTO dto) {
        Empleado empleado = empleadoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Empleado", "id", id));
        
        Empleado empleadoActualizado = mapper.toEmpleadoEntity(dto);
        empleadoActualizado.setId(id);
        
        Empleado resultado = empleadoRepository.save(empleadoActualizado);
        return mapper.toEmpleadoResponse(resultado);
    }
    
    public void eliminar(Long id) {
        if (!empleadoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Empleado", "id", id);
        }
        empleadoRepository.deleteById(id);
    }
}

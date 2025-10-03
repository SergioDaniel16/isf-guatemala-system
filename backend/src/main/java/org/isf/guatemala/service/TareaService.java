package org.isf.guatemala.service;

import org.isf.guatemala.dto.request.TareaRequestDTO;
import org.isf.guatemala.dto.response.TareaResponseDTO;
import org.isf.guatemala.exception.ResourceNotFoundException;
import org.isf.guatemala.mapper.EntityMapper;
import org.isf.guatemala.model.Tarea;
import org.isf.guatemala.repository.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class TareaService {
    
    @Autowired
    private TareaRepository tareaRepository;
    
    @Autowired
    private EntityMapper mapper;
    
    public List<TareaResponseDTO> obtenerTodas() {
        List<Tarea> tareas = tareaRepository.findAll();
        return mapper.toTareaResponseList(tareas);
    }
    
    public TareaResponseDTO obtenerPorId(Long id) {
        Tarea tarea = tareaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Tarea", "id", id));
        return mapper.toTareaResponse(tarea);
    }
    
    public TareaResponseDTO crear(TareaRequestDTO dto) {
        Tarea tarea = mapper.toTareaEntity(dto);
        Tarea tareaGuardada = tareaRepository.save(tarea);
        return mapper.toTareaResponse(tareaGuardada);
    }
    
    public TareaResponseDTO actualizar(Long id, TareaRequestDTO dto) {
        Tarea tarea = tareaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Tarea", "id", id));
        
        tarea.setNombre(dto.getNombre());
        tarea.setDefinicion(dto.getDefinicion());
        tarea.setEsCobrable(dto.getEsCobrable());
        
        Tarea tareaActualizada = tareaRepository.save(tarea);
        return mapper.toTareaResponse(tareaActualizada);
    }
    
    public void eliminar(Long id) {
        if (!tareaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tarea", "id", id);
        }
        tareaRepository.deleteById(id);
    }
}

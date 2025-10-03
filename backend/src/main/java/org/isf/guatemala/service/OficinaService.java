package org.isf.guatemala.service;

import org.isf.guatemala.dto.request.OficinaRequestDTO;
import org.isf.guatemala.dto.response.OficinaResponseDTO;
import org.isf.guatemala.exception.DuplicateResourceException;
import org.isf.guatemala.exception.ResourceNotFoundException;
import org.isf.guatemala.mapper.EntityMapper;
import org.isf.guatemala.model.Oficina;
import org.isf.guatemala.repository.OficinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class OficinaService {
    
    @Autowired
    private OficinaRepository oficinaRepository;
    
    @Autowired
    private EntityMapper mapper;
    
    public List<OficinaResponseDTO> obtenerTodas() {
        List<Oficina> oficinas = oficinaRepository.findAll();
        return mapper.toOficinaResponseList(oficinas);
    }
    
    public OficinaResponseDTO obtenerPorId(Long id) {
        Oficina oficina = oficinaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Oficina", "id", id));
        return mapper.toOficinaResponse(oficina);
    }
    
    public OficinaResponseDTO crear(OficinaRequestDTO dto) {
        if (oficinaRepository.existsByNombre(dto.getNombre())) {
            throw new DuplicateResourceException("Oficina", "nombre", dto.getNombre());
        }
        
        Oficina oficina = mapper.toOficinaEntity(dto);
        Oficina oficinaGuardada = oficinaRepository.save(oficina);
        return mapper.toOficinaResponse(oficinaGuardada);
    }
    
    public OficinaResponseDTO actualizar(Long id, OficinaRequestDTO dto) {
        Oficina oficina = oficinaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Oficina", "id", id));
        
        // Verificar si el nuevo nombre ya existe en otra oficina
        if (!oficina.getNombre().equals(dto.getNombre()) && 
            oficinaRepository.existsByNombre(dto.getNombre())) {
            throw new DuplicateResourceException("Oficina", "nombre", dto.getNombre());
        }
        
        oficina.setNombre(dto.getNombre());
        oficina.setDescripcion(dto.getDescripcion());
        
        Oficina oficinaActualizada = oficinaRepository.save(oficina);
        return mapper.toOficinaResponse(oficinaActualizada);
    }
    
    public void eliminar(Long id) {
        if (!oficinaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Oficina", "id", id);
        }
        oficinaRepository.deleteById(id);
    }
}
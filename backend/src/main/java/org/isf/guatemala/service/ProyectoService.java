package org.isf.guatemala.service;

import org.isf.guatemala.dto.request.ProyectoRequestDTO;
import org.isf.guatemala.dto.response.ProyectoResponseDTO;
import org.isf.guatemala.exception.DuplicateResourceException;
import org.isf.guatemala.exception.ResourceNotFoundException;
import org.isf.guatemala.mapper.EntityMapper;
import org.isf.guatemala.model.Proyecto;
import org.isf.guatemala.repository.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class ProyectoService {
    
    @Autowired
    private ProyectoRepository proyectoRepository;
    
    @Autowired
    private EntityMapper mapper;
    
    public List<ProyectoResponseDTO> obtenerTodos() {
        List<Proyecto> proyectos = proyectoRepository.findAll();
        return mapper.toProyectoResponseList(proyectos);
    }
    
    public ProyectoResponseDTO obtenerPorId(Long id) {
        Proyecto proyecto = proyectoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Proyecto", "id", id));
        return mapper.toProyectoResponse(proyecto);
    }
    
    public List<ProyectoResponseDTO> obtenerPorOficina(Long oficinaId) {
        List<Proyecto> proyectos = proyectoRepository.findByOficinaId(oficinaId);
        return mapper.toProyectoResponseList(proyectos);
    }
    
    public ProyectoResponseDTO crear(ProyectoRequestDTO dto) {
        if (proyectoRepository.existsByCodigo(dto.getCodigo())) {
            throw new DuplicateResourceException("Proyecto", "código", dto.getCodigo());
        }
        
        Proyecto proyecto = mapper.toProyectoEntity(dto);
        Proyecto proyectoGuardado = proyectoRepository.save(proyecto);
        return mapper.toProyectoResponse(proyectoGuardado);
    }
    
    public ProyectoResponseDTO actualizar(Long id, ProyectoRequestDTO dto) {
        Proyecto proyecto = proyectoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Proyecto", "id", id));
        
        // Verificar si el nuevo código ya existe en otro proyecto
        if (!proyecto.getCodigo().equals(dto.getCodigo()) && 
            proyectoRepository.existsByCodigo(dto.getCodigo())) {
            throw new DuplicateResourceException("Proyecto", "código", dto.getCodigo());
        }
        
        Proyecto proyectoActualizado = mapper.toProyectoEntity(dto);
        proyectoActualizado.setId(id);
        
        Proyecto resultado = proyectoRepository.save(proyectoActualizado);
        return mapper.toProyectoResponse(resultado);
    }
    
    public void eliminar(Long id) {
        if (!proyectoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Proyecto", "id", id);
        }
        proyectoRepository.deleteById(id);
    }
}

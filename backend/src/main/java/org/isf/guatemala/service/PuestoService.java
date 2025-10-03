package org.isf.guatemala.service;

import org.isf.guatemala.dto.request.PuestoRequestDTO;
import org.isf.guatemala.dto.response.PuestoResponseDTO;
import org.isf.guatemala.exception.ResourceNotFoundException;
import org.isf.guatemala.mapper.EntityMapper;
import org.isf.guatemala.model.Puesto;
import org.isf.guatemala.repository.PuestoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class PuestoService {
    
    @Autowired
    private PuestoRepository puestoRepository;
    
    @Autowired
    private EntityMapper mapper;
    
    public List<PuestoResponseDTO> obtenerTodos() {
        List<Puesto> puestos = puestoRepository.findAll();
        return mapper.toPuestoResponseList(puestos);
    }
    
    public PuestoResponseDTO obtenerPorId(Long id) {
        Puesto puesto = puestoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Puesto", "id", id));
        return mapper.toPuestoResponse(puesto);
    }
    
    public PuestoResponseDTO crear(PuestoRequestDTO dto) {
        Puesto puesto = mapper.toPuestoEntity(dto);
        Puesto puestoGuardado = puestoRepository.save(puesto);
        return mapper.toPuestoResponse(puestoGuardado);
    }
    
    public PuestoResponseDTO actualizar(Long id, PuestoRequestDTO dto) {
        Puesto puesto = puestoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Puesto", "id", id));
        
        puesto.setNombre(dto.getNombre());
        puesto.setDetalle(dto.getDetalle());
        puesto.setSalarioPorHora(dto.getSalarioPorHora());
        
        Puesto puestoActualizado = puestoRepository.save(puesto);
        return mapper.toPuestoResponse(puestoActualizado);
    }
    
    public void eliminar(Long id) {
        if (!puestoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Puesto", "id", id);
        }
        puestoRepository.deleteById(id);
    }
}

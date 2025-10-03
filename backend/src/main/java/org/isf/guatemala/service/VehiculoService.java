package org.isf.guatemala.service;

import org.isf.guatemala.dto.request.VehiculoRequestDTO;
import org.isf.guatemala.dto.response.VehiculoResponseDTO;
import org.isf.guatemala.exception.DuplicateResourceException;
import org.isf.guatemala.exception.ResourceNotFoundException;
import org.isf.guatemala.mapper.EntityMapper;
import org.isf.guatemala.model.Vehiculo;
import org.isf.guatemala.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class VehiculoService {
    
    @Autowired
    private VehiculoRepository vehiculoRepository;
    
    @Autowired
    private EntityMapper mapper;
    
    public List<VehiculoResponseDTO> obtenerTodos() {
        List<Vehiculo> vehiculos = vehiculoRepository.findAll();
        return mapper.toVehiculoResponseList(vehiculos);
    }
    
    public List<VehiculoResponseDTO> obtenerActivos() {
        List<Vehiculo> vehiculos = vehiculoRepository.findByActivo(true);
        return mapper.toVehiculoResponseList(vehiculos);
    }
    
    public VehiculoResponseDTO obtenerPorId(Long id) {
        Vehiculo vehiculo = vehiculoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Vehículo", "id", id));
        return mapper.toVehiculoResponse(vehiculo);
    }
    
    public VehiculoResponseDTO crear(VehiculoRequestDTO dto) {
        if (vehiculoRepository.existsByPlaca(dto.getPlaca())) {
            throw new DuplicateResourceException("Vehículo", "placa", dto.getPlaca());
        }
        
        Vehiculo vehiculo = mapper.toVehiculoEntity(dto);
        Vehiculo vehiculoGuardado = vehiculoRepository.save(vehiculo);
        return mapper.toVehiculoResponse(vehiculoGuardado);
    }
    
    public VehiculoResponseDTO actualizar(Long id, VehiculoRequestDTO dto) {
        Vehiculo vehiculo = vehiculoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Vehículo", "id", id));
        
        // Verificar si la nueva placa ya existe en otro vehículo
        if (!vehiculo.getPlaca().equals(dto.getPlaca()) && 
            vehiculoRepository.existsByPlaca(dto.getPlaca())) {
            throw new DuplicateResourceException("Vehículo", "placa", dto.getPlaca());
        }
        
        Vehiculo vehiculoActualizado = mapper.toVehiculoEntity(dto);
        vehiculoActualizado.setId(id);
        
        Vehiculo resultado = vehiculoRepository.save(vehiculoActualizado);
        return mapper.toVehiculoResponse(resultado);
    }
    
    public void eliminar(Long id) {
        if (!vehiculoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehículo", "id", id);
        }
        vehiculoRepository.deleteById(id);
    }
}

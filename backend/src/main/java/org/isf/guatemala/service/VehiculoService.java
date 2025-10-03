package org.isf.guatemala.service;

import org.isf.guatemala.model.Vehiculo;
import org.isf.guatemala.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VehiculoService {
    
    @Autowired
    private VehiculoRepository vehiculoRepository;
    
    public List<Vehiculo> obtenerTodos() {
        return vehiculoRepository.findAll();
    }
    
    public List<Vehiculo> obtenerActivos() {
        return vehiculoRepository.findByActivo(true);
    }
    
    public Optional<Vehiculo> obtenerPorId(Long id) {
        return vehiculoRepository.findById(id);
    }
    
    public Vehiculo crear(Vehiculo vehiculo) {
        if (vehiculoRepository.existsByPlaca(vehiculo.getPlaca())) {
            throw new RuntimeException("Ya existe un vehículo con esa placa");
        }
        return vehiculoRepository.save(vehiculo);
    }
    
    public Vehiculo actualizar(Long id, Vehiculo vehiculo) {
        if (!vehiculoRepository.existsById(id)) {
            throw new RuntimeException("Vehículo no encontrado");
        }
        vehiculo.setId(id);
        return vehiculoRepository.save(vehiculo);
    }
    
    public void eliminar(Long id) {
        vehiculoRepository.deleteById(id);
    }
}

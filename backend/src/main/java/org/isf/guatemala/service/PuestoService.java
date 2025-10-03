package org.isf.guatemala.service;

import org.isf.guatemala.model.Puesto;
import org.isf.guatemala.repository.PuestoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PuestoService {
    
    @Autowired
    private PuestoRepository puestoRepository;
    
    public List<Puesto> obtenerTodos() {
        return puestoRepository.findAll();
    }
    
    public Optional<Puesto> obtenerPorId(Long id) {
        return puestoRepository.findById(id);
    }
    
    public Puesto crear(Puesto puesto) {
        return puestoRepository.save(puesto);
    }
    
    public Puesto actualizar(Long id, Puesto puesto) {
        if (!puestoRepository.existsById(id)) {
            throw new RuntimeException("Puesto no encontrado");
        }
        puesto.setId(id);
        return puestoRepository.save(puesto);
    }
    
    public void eliminar(Long id) {
        puestoRepository.deleteById(id);
    }
}

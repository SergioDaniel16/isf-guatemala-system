package org.isf.guatemala.service;

import org.isf.guatemala.model.Oficina;
import org.isf.guatemala.repository.OficinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OficinaService {
    
    @Autowired
    private OficinaRepository oficinaRepository;
    
    public List<Oficina> obtenerTodas() {
        return oficinaRepository.findAll();
    }
    
    public Optional<Oficina> obtenerPorId(Long id) {
        return oficinaRepository.findById(id);
    }
    
    public Oficina crear(Oficina oficina) {
        if (oficinaRepository.existsByNombre(oficina.getNombre())) {
            throw new RuntimeException("Ya existe una oficina con ese nombre");
        }
        return oficinaRepository.save(oficina);
    }
    
    public Oficina actualizar(Long id, Oficina oficina) {
        if (!oficinaRepository.existsById(id)) {
            throw new RuntimeException("Oficina no encontrada");
        }
        oficina.setId(id);
        return oficinaRepository.save(oficina);
    }
    
    public void eliminar(Long id) {
        oficinaRepository.deleteById(id);
    }
}

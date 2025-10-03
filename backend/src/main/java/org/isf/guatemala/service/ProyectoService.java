package org.isf.guatemala.service;

import org.isf.guatemala.model.Proyecto;
import org.isf.guatemala.repository.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProyectoService {
    
    @Autowired
    private ProyectoRepository proyectoRepository;
    
    public List<Proyecto> obtenerTodos() {
        return proyectoRepository.findAll();
    }
    
    public Optional<Proyecto> obtenerPorId(Long id) {
        return proyectoRepository.findById(id);
    }
    
    public List<Proyecto> obtenerPorOficina(Long oficinaId) {
        return proyectoRepository.findByOficinaId(oficinaId);
    }
    
    public Proyecto crear(Proyecto proyecto) {
        if (proyectoRepository.existsByCodigo(proyecto.getCodigo())) {
            throw new RuntimeException("Ya existe un proyecto con ese código");
        }
        return proyectoRepository.save(proyecto);
    }
    
    public Proyecto actualizar(Long id, Proyecto proyecto) {
        if (!proyectoRepository.existsById(id)) {
            throw new RuntimeException("Proyecto no encontrado");
        }
        proyecto.setId(id);
        return proyectoRepository.save(proyecto);
    }
    
    public void eliminar(Long id) {
        proyectoRepository.deleteById(id);
    }
}

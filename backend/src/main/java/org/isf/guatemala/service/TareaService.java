package org.isf.guatemala.service;

import org.isf.guatemala.model.Tarea;
import org.isf.guatemala.repository.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TareaService {
    
    @Autowired
    private TareaRepository tareaRepository;
    
    public List<Tarea> obtenerTodas() {
        return tareaRepository.findAll();
    }
    
    public Optional<Tarea> obtenerPorId(Long id) {
        return tareaRepository.findById(id);
    }
    
    public Tarea crear(Tarea tarea) {
        return tareaRepository.save(tarea);
    }
    
    public Tarea actualizar(Long id, Tarea tarea) {
        if (!tareaRepository.existsById(id)) {
            throw new RuntimeException("Tarea no encontrada");
        }
        tarea.setId(id);
        return tareaRepository.save(tarea);
    }
    
    public void eliminar(Long id) {
        tareaRepository.deleteById(id);
    }
}

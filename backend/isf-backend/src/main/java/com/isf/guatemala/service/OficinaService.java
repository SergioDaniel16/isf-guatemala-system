package com.isf.guatemala.service;

import com.isf.guatemala.entity.Oficina;
import com.isf.guatemala.repository.OficinaRepository;
import com.isf.guatemala.dto.CreateOficinaRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OficinaService {
    
    @Autowired
    private OficinaRepository oficinaRepository;
    
    // Crear nueva oficina
    public Oficina crearOficina(CreateOficinaRequest request) {
        // Verificar que no exista una oficina con el mismo nombre
        if (oficinaRepository.existsByNombre(request.getNombre())) {
            throw new RuntimeException("Ya existe una oficina con el nombre: " + request.getNombre());
        }
        
        Oficina oficina = new Oficina(request.getNombre(), request.getDescripcion());
        return oficinaRepository.save(oficina);
    }
    
    // Obtener todas las oficinas
    public List<Oficina> obtenerTodasLasOficinas() {
        return oficinaRepository.findAll();
    }
    
    // Obtener oficinas activas
    public List<Oficina> obtenerOficinasActivas() {
        return oficinaRepository.findByActivoTrue();
    }
    
    // Obtener oficina por ID
    public Optional<Oficina> obtenerOficinaPorId(Long id) {
        return oficinaRepository.findById(id);
    }
    
    // Obtener oficina por nombre
    public Optional<Oficina> obtenerOficinaPorNombre(String nombre) {
        return oficinaRepository.findByNombre(nombre);
    }
    
    // Actualizar oficina
    public Oficina actualizarOficina(Long id, CreateOficinaRequest request) {
        Oficina oficina = oficinaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Oficina no encontrada con ID: " + id));
        
        // Verificar que no exista otra oficina con el mismo nombre
        if (!oficina.getNombre().equals(request.getNombre()) && 
            oficinaRepository.existsByNombre(request.getNombre())) {
            throw new RuntimeException("Ya existe una oficina con el nombre: " + request.getNombre());
        }
        
        oficina.setNombre(request.getNombre());
        oficina.setDescripcion(request.getDescripcion());
        
        return oficinaRepository.save(oficina);
    }
    
    // Desactivar oficina (soft delete)
    public Oficina desactivarOficina(Long id) {
        Oficina oficina = oficinaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Oficina no encontrada con ID: " + id));
        
        oficina.setActivo(false);
        return oficinaRepository.save(oficina);
    }
    
    // Activar oficina
    public Oficina activarOficina(Long id) {
        Oficina oficina = oficinaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Oficina no encontrada con ID: " + id));
        
        oficina.setActivo(true);
        return oficinaRepository.save(oficina);
    }
    
    // Obtener oficinas con proyectos activos
    public List<Oficina> obtenerOficinasConProyectosActivos() {
        return oficinaRepository.findOficinasWithActiveProjects();
    }
    
    // Verificar si oficina existe
    public boolean existeOficina(String nombre) {
        return oficinaRepository.existsByNombre(nombre);
    }
}

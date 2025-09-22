package com.isf.guatemala.service;

import com.isf.guatemala.entity.Puesto;
import com.isf.guatemala.repository.PuestoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PuestoService {
    
    @Autowired
    private PuestoRepository puestoRepository;
    
    // Crear nuevo puesto
    public Puesto crearPuesto(String nombre, String detalle, BigDecimal salarioPorHora) {
        // Verificar que no exista un puesto con el mismo nombre
        if (puestoRepository.existsByNombre(nombre)) {
            throw new RuntimeException("Ya existe un puesto con el nombre: " + nombre);
        }
        
        // Validar salario
        if (salarioPorHora == null || salarioPorHora.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("El salario por hora debe ser mayor a cero");
        }
        
        Puesto puesto = new Puesto(nombre, detalle, salarioPorHora);
        return puestoRepository.save(puesto);
    }
    
    // Obtener todos los puestos
    public List<Puesto> obtenerTodosLosPuestos() {
        return puestoRepository.findAll();
    }
    
    // Obtener puestos activos
    public List<Puesto> obtenerPuestosActivos() {
        return puestoRepository.findByActivoTrue();
    }
    
    // Obtener puesto por ID
    public Optional<Puesto> obtenerPuestoPorId(Long id) {
        return puestoRepository.findById(id);
    }
    
    // Obtener puesto por nombre
    public Optional<Puesto> obtenerPuestoPorNombre(String nombre) {
        return puestoRepository.findByNombre(nombre);
    }
    
    // Actualizar puesto
    public Puesto actualizarPuesto(Long id, String nombre, String detalle, BigDecimal salarioPorHora) {
        Puesto puesto = puestoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Puesto no encontrado con ID: " + id));
        
        // Verificar que no exista otro puesto con el mismo nombre
        if (!puesto.getNombre().equals(nombre) && puestoRepository.existsByNombre(nombre)) {
            throw new RuntimeException("Ya existe un puesto con el nombre: " + nombre);
        }
        
        // Validar salario
        if (salarioPorHora == null || salarioPorHora.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("El salario por hora debe ser mayor a cero");
        }
        
        puesto.setNombre(nombre);
        puesto.setDetalle(detalle);
        puesto.setSalarioPorHora(salarioPorHora);
        
        return puestoRepository.save(puesto);
    }
    
    // Desactivar puesto
    public Puesto desactivarPuesto(Long id) {
        Puesto puesto = puestoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Puesto no encontrado con ID: " + id));
        
        puesto.setActivo(false);
        return puestoRepository.save(puesto);
    }
    
    // Activar puesto
    public Puesto activarPuesto(Long id) {
        Puesto puesto = puestoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Puesto no encontrado con ID: " + id));
        
        puesto.setActivo(true);
        return puestoRepository.save(puesto);
    }
    
    // Buscar puestos por rango salarial
    public List<Puesto> obtenerPuestosPorRangoSalarial(BigDecimal salarioMin, BigDecimal salarioMax) {
        return puestoRepository.findBySalarioPorHoraBetweenAndActivoTrue(salarioMin, salarioMax);
    }
    
    // Obtener puestos ordenados por salario descendente
    public List<Puesto> obtenerPuestosOrdenadosPorSalario() {
        return puestoRepository.findActivePuestosOrderBySalaryDesc();
    }
    
    // Verificar si puesto existe
    public boolean existePuesto(String nombre) {
        return puestoRepository.existsByNombre(nombre);
    }
}

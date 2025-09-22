package com.isf.guatemala.service;

import com.isf.guatemala.entity.Proyecto;
import com.isf.guatemala.entity.Oficina;
import com.isf.guatemala.repository.ProyectoRepository;
import com.isf.guatemala.repository.OficinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProyectoService {
    
    @Autowired
    private ProyectoRepository proyectoRepository;
    
    @Autowired
    private OficinaRepository oficinaRepository;
    
    // Crear nuevo proyecto
    public Proyecto crearProyecto(String codigo, String nombre, String departamento, 
                                 String municipio, Proyecto.TipoProyecto tipo, 
                                 BigDecimal cambioDolar, BigDecimal tarifaBaseVehiculo, 
                                 BigDecimal tarifaKmExtra, Long oficinaId) {
        
        // Validar campos obligatorios
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new RuntimeException("El código del proyecto es obligatorio");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new RuntimeException("El nombre del proyecto es obligatorio");
        }
        if (departamento == null || departamento.trim().isEmpty()) {
            throw new RuntimeException("El departamento es obligatorio");
        }
        if (municipio == null || municipio.trim().isEmpty()) {
            throw new RuntimeException("El municipio es obligatorio");
        }
        
        // Verificar que no exista proyecto con el mismo código
        if (proyectoRepository.existsByCodigo(codigo)) {
            throw new RuntimeException("Ya existe un proyecto con el código: " + codigo);
        }
        
        // Validar valores monetarios
        if (cambioDolar == null || cambioDolar.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("El cambio de dólar debe ser mayor a cero");
        }
        if (tarifaBaseVehiculo == null || tarifaBaseVehiculo.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("La tarifa base del vehículo no puede ser negativa");
        }
        if (tarifaKmExtra == null || tarifaKmExtra.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("La tarifa por Km extra no puede ser negativa");
        }
        
        // Verificar que la oficina existe
        Oficina oficina = oficinaRepository.findById(oficinaId)
            .orElseThrow(() -> new RuntimeException("Oficina no encontrada con ID: " + oficinaId));
        
        if (!oficina.getActivo()) {
            throw new RuntimeException("No se puede asignar un proyecto a una oficina inactiva");
        }
        
        Proyecto proyecto = new Proyecto(codigo, nombre, departamento, municipio, 
                                       tipo, cambioDolar, tarifaBaseVehiculo, 
                                       tarifaKmExtra, oficina);
        
        return proyectoRepository.save(proyecto);
    }
    
    // Obtener todos los proyectos
    public List<Proyecto> obtenerTodosLosProyectos() {
        return proyectoRepository.findAll();
    }
    
    // Obtener proyectos activos
    public List<Proyecto> obtenerProyectosActivos() {
        return proyectoRepository.findByActivoTrue();
    }
    
    // Obtener proyecto por ID
    public Optional<Proyecto> obtenerProyectoPorId(Long id) {
        return proyectoRepository.findById(id);
    }
    
    // Obtener proyecto por código
    public Optional<Proyecto> obtenerProyectoPorCodigo(String codigo) {
        return proyectoRepository.findByCodigo(codigo);
    }
    
    // Obtener proyectos por oficina
    public List<Proyecto> obtenerProyectosPorOficina(Long oficinaId) {
        return proyectoRepository.findByOficinaIdAndActivoTrue(oficinaId);
    }
    
    // Buscar proyectos por nombre
    public List<Proyecto> buscarProyectosPorNombre(String nombre) {
        return proyectoRepository.findByNombreContaining(nombre);
    }
    
    // Obtener proyectos por tipo
    public List<Proyecto> obtenerProyectosPorTipo(Proyecto.TipoProyecto tipo) {
        return proyectoRepository.findByTipoAndActivoTrue(tipo);
    }
    
    // Obtener proyectos por departamento
    public List<Proyecto> obtenerProyectosPorDepartamento(String departamento) {
        return proyectoRepository.findByDepartamentoAndActivoTrue(departamento);
    }
    
    // Actualizar proyecto
    public Proyecto actualizarProyecto(Long id, String codigo, String nombre, 
                                      String departamento, String municipio, 
                                      Proyecto.TipoProyecto tipo, BigDecimal cambioDolar, 
                                      BigDecimal tarifaBaseVehiculo, BigDecimal tarifaKmExtra, 
                                      Long oficinaId) {
        
        Proyecto proyecto = proyectoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Proyecto no encontrado con ID: " + id));
        
        // Validar campos obligatorios
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new RuntimeException("El código del proyecto es obligatorio");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new RuntimeException("El nombre del proyecto es obligatorio");
        }
        
        // Verificar que no exista otro proyecto con el mismo código
        if (!proyecto.getCodigo().equals(codigo) && proyectoRepository.existsByCodigo(codigo)) {
            throw new RuntimeException("Ya existe un proyecto con el código: " + codigo);
        }
        
        // Validar valores monetarios
        if (cambioDolar == null || cambioDolar.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("El cambio de dólar debe ser mayor a cero");
        }
        if (tarifaBaseVehiculo == null || tarifaBaseVehiculo.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("La tarifa base del vehículo no puede ser negativa");
        }
        if (tarifaKmExtra == null || tarifaKmExtra.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("La tarifa por Km extra no puede ser negativa");
        }
        
        // Verificar que la oficina existe
        Oficina oficina = oficinaRepository.findById(oficinaId)
            .orElseThrow(() -> new RuntimeException("Oficina no encontrada con ID: " + oficinaId));
        
        // Actualizar campos
        proyecto.setCodigo(codigo);
        proyecto.setNombre(nombre);
        proyecto.setDepartamento(departamento);
        proyecto.setMunicipio(municipio);
        proyecto.setTipo(tipo);
        proyecto.setCambioDolar(cambioDolar);
        proyecto.setTarifaBaseVehiculo(tarifaBaseVehiculo);
        proyecto.setTarifaKmExtra(tarifaKmExtra);
        proyecto.setOficina(oficina);
        
        return proyectoRepository.save(proyecto);
    }
    
    // Desactivar proyecto
    public Proyecto desactivarProyecto(Long id) {
        Proyecto proyecto = proyectoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Proyecto no encontrado con ID: " + id));
        
        proyecto.setActivo(false);
        return proyectoRepository.save(proyecto);
    }
    
    // Activar proyecto
    public Proyecto activarProyecto(Long id) {
        Proyecto proyecto = proyectoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Proyecto no encontrado con ID: " + id));
        
        proyecto.setActivo(true);
        return proyectoRepository.save(proyecto);
    }
    
    // Verificar si proyecto existe
    public boolean existeProyecto(String codigo) {
        return proyectoRepository.existsByCodigo(codigo);
    }
}

package com.isf.guatemala.service;

import com.isf.guatemala.entity.Empleado;
import com.isf.guatemala.entity.Puesto;
import com.isf.guatemala.repository.EmpleadoRepository;
import com.isf.guatemala.repository.PuestoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmpleadoService {
    
    @Autowired
    private EmpleadoRepository empleadoRepository;
    
    @Autowired
    private PuestoRepository puestoRepository;
    
    // Crear nuevo empleado
    public Empleado crearEmpleado(String primerNombre, String segundoNombre, 
                                 String primerApellido, String segundoApellido, 
                                 String contacto, Long puestoId) {
        
        // Validar campos obligatorios
        if (primerNombre == null || primerNombre.trim().isEmpty()) {
            throw new RuntimeException("El primer nombre es obligatorio");
        }
        if (primerApellido == null || primerApellido.trim().isEmpty()) {
            throw new RuntimeException("El primer apellido es obligatorio");
        }
        if (contacto == null || contacto.trim().isEmpty()) {
            throw new RuntimeException("El contacto es obligatorio");
        }
        
        // Validar formato de contacto (8 dígitos)
        if (!contacto.matches("\\d{8}")) {
            throw new RuntimeException("El contacto debe tener exactamente 8 dígitos");
        }
        
        // Verificar que no exista empleado con el mismo contacto
        if (empleadoRepository.existsByContacto(contacto)) {
            throw new RuntimeException("Ya existe un empleado con el contacto: " + contacto);
        }
        
        // Verificar que el puesto existe
        Puesto puesto = puestoRepository.findById(puestoId)
            .orElseThrow(() -> new RuntimeException("Puesto no encontrado con ID: " + puestoId));
        
        if (!puesto.getActivo()) {
            throw new RuntimeException("No se puede asignar un puesto inactivo");
        }
        
        Empleado empleado = new Empleado(primerNombre, primerApellido, contacto, puesto);
        empleado.setSegundoNombre(segundoNombre);
        empleado.setSegundoApellido(segundoApellido);
        
        return empleadoRepository.save(empleado);
    }
    
    // Obtener todos los empleados
    public List<Empleado> obtenerTodosLosEmpleados() {
        return empleadoRepository.findAll();
    }
    
    // Obtener empleados activos
    public List<Empleado> obtenerEmpleadosActivos() {
        return empleadoRepository.findByActivoTrue();
    }
    
    // Obtener empleado por ID
    public Optional<Empleado> obtenerEmpleadoPorId(Long id) {
        return empleadoRepository.findById(id);
    }
    
    // Obtener empleado por contacto
    public Optional<Empleado> obtenerEmpleadoPorContacto(String contacto) {
        return empleadoRepository.findByContacto(contacto);
    }
    
    // Buscar empleados por nombre
    public List<Empleado> buscarEmpleadosPorNombre(String nombre) {
        return empleadoRepository.findByNombreContaining(nombre);
    }
    
    // Obtener empleados por puesto
    public List<Empleado> obtenerEmpleadosPorPuesto(Long puestoId) {
        return empleadoRepository.findByPuestoIdAndActivoTrue(puestoId);
    }
    
    // Actualizar empleado
    public Empleado actualizarEmpleado(Long id, String primerNombre, String segundoNombre,
                                      String primerApellido, String segundoApellido,
                                      String contacto, Long puestoId) {
        
        Empleado empleado = empleadoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Empleado no encontrado con ID: " + id));
        
        // Validar campos obligatorios
        if (primerNombre == null || primerNombre.trim().isEmpty()) {
            throw new RuntimeException("El primer nombre es obligatorio");
        }
        if (primerApellido == null || primerApellido.trim().isEmpty()) {
            throw new RuntimeException("El primer apellido es obligatorio");
        }
        if (contacto == null || contacto.trim().isEmpty()) {
            throw new RuntimeException("El contacto es obligatorio");
        }
        
        // Validar formato de contacto
        if (!contacto.matches("\\d{8}")) {
            throw new RuntimeException("El contacto debe tener exactamente 8 dígitos");
        }
        
        // Verificar que no exista otro empleado con el mismo contacto
        if (!empleado.getContacto().equals(contacto) && empleadoRepository.existsByContacto(contacto)) {
            throw new RuntimeException("Ya existe un empleado con el contacto: " + contacto);
        }
        
        // Verificar que el puesto existe
        Puesto puesto = puestoRepository.findById(puestoId)
            .orElseThrow(() -> new RuntimeException("Puesto no encontrado con ID: " + puestoId));
        
        if (!puesto.getActivo()) {
            throw new RuntimeException("No se puede asignar un puesto inactivo");
        }
        
        // Actualizar campos
        empleado.setPrimerNombre(primerNombre);
        empleado.setSegundoNombre(segundoNombre);
        empleado.setPrimerApellido(primerApellido);
        empleado.setSegundoApellido(segundoApellido);
        empleado.setContacto(contacto);
        empleado.setPuesto(puesto);
        
        return empleadoRepository.save(empleado);
    }
    
    // Desactivar empleado
    public Empleado desactivarEmpleado(Long id) {
        Empleado empleado = empleadoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Empleado no encontrado con ID: " + id));
        
        empleado.setActivo(false);
        return empleadoRepository.save(empleado);
    }
    
    // Activar empleado
    public Empleado activarEmpleado(Long id) {
        Empleado empleado = empleadoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Empleado no encontrado con ID: " + id));
        
        empleado.setActivo(true);
        return empleadoRepository.save(empleado);
    }
    
    // Obtener empleados con registros de horas
    public List<Empleado> obtenerEmpleadosConRegistrosHoras() {
        return empleadoRepository.findEmpleadosWithHoursRecords();
    }
    
    // Verificar si empleado existe por contacto
    public boolean existeEmpleado(String contacto) {
        return empleadoRepository.existsByContacto(contacto);
    }
}

package org.isf.guatemala.service;

import org.isf.guatemala.model.Empleado;
import org.isf.guatemala.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService {
    
    @Autowired
    private EmpleadoRepository empleadoRepository;
    
    public List<Empleado> obtenerTodos() {
        return empleadoRepository.findAll();
    }
    
    public Optional<Empleado> obtenerPorId(Long id) {
        return empleadoRepository.findById(id);
    }
    
    public Empleado crear(Empleado empleado) {
        // Validar que el contacto sea de 8 dígitos
        if (empleado.getContacto() == null || empleado.getContacto().length() != 8) {
            throw new RuntimeException("El contacto debe tener 8 dígitos");
        }
        return empleadoRepository.save(empleado);
    }
    
    public Empleado actualizar(Long id, Empleado empleado) {
        if (!empleadoRepository.existsById(id)) {
            throw new RuntimeException("Empleado no encontrado");
        }
        empleado.setId(id);
        return empleadoRepository.save(empleado);
    }
    
    public void eliminar(Long id) {
        empleadoRepository.deleteById(id);
    }
}

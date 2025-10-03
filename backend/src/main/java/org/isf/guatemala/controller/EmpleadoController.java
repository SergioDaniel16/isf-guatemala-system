package org.isf.guatemala.controller;

import org.isf.guatemala.model.Empleado;
import org.isf.guatemala.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/empleados")
@CrossOrigin(origins = "*")
public class EmpleadoController {
    
    @Autowired
    private EmpleadoService empleadoService;
    
    @GetMapping
    public ResponseEntity<List<Empleado>> obtenerTodos() {
        return ResponseEntity.ok(empleadoService.obtenerTodos());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Empleado> obtenerPorId(@PathVariable Long id) {
        return empleadoService.obtenerPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Empleado> crear(@RequestBody Empleado empleado) {
        return ResponseEntity.ok(empleadoService.crear(empleado));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Empleado> actualizar(@PathVariable Long id, @RequestBody Empleado empleado) {
        return ResponseEntity.ok(empleadoService.actualizar(id, empleado));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        empleadoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

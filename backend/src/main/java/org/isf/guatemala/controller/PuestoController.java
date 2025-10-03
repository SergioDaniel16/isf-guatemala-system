package org.isf.guatemala.controller;

import org.isf.guatemala.model.Puesto;
import org.isf.guatemala.service.PuestoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/puestos")
@CrossOrigin(origins = "*")
public class PuestoController {
    
    @Autowired
    private PuestoService puestoService;
    
    @GetMapping
    public ResponseEntity<List<Puesto>> obtenerTodos() {
        return ResponseEntity.ok(puestoService.obtenerTodos());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Puesto> obtenerPorId(@PathVariable Long id) {
        return puestoService.obtenerPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Puesto> crear(@RequestBody Puesto puesto) {
        return ResponseEntity.ok(puestoService.crear(puesto));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Puesto> actualizar(@PathVariable Long id, @RequestBody Puesto puesto) {
        return ResponseEntity.ok(puestoService.actualizar(id, puesto));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        puestoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

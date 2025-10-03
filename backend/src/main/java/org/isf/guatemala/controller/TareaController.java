package org.isf.guatemala.controller;

import org.isf.guatemala.model.Tarea;
import org.isf.guatemala.service.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tareas")
@CrossOrigin(origins = "*")
public class TareaController {
    
    @Autowired
    private TareaService tareaService;
    
    @GetMapping
    public ResponseEntity<List<Tarea>> obtenerTodas() {
        return ResponseEntity.ok(tareaService.obtenerTodas());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Tarea> obtenerPorId(@PathVariable Long id) {
        return tareaService.obtenerPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Tarea> crear(@RequestBody Tarea tarea) {
        return ResponseEntity.ok(tareaService.crear(tarea));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Tarea> actualizar(@PathVariable Long id, @RequestBody Tarea tarea) {
        return ResponseEntity.ok(tareaService.actualizar(id, tarea));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        tareaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

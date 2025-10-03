package org.isf.guatemala.controller;

import org.isf.guatemala.model.Proyecto;
import org.isf.guatemala.service.ProyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/proyectos")
@CrossOrigin(origins = "*")
public class ProyectoController {
    
    @Autowired
    private ProyectoService proyectoService;
    
    @GetMapping
    public ResponseEntity<List<Proyecto>> obtenerTodos() {
        return ResponseEntity.ok(proyectoService.obtenerTodos());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Proyecto> obtenerPorId(@PathVariable Long id) {
        return proyectoService.obtenerPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/oficina/{oficinaId}")
    public ResponseEntity<List<Proyecto>> obtenerPorOficina(@PathVariable Long oficinaId) {
        return ResponseEntity.ok(proyectoService.obtenerPorOficina(oficinaId));
    }
    
    @PostMapping
    public ResponseEntity<Proyecto> crear(@RequestBody Proyecto proyecto) {
        return ResponseEntity.ok(proyectoService.crear(proyecto));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Proyecto> actualizar(@PathVariable Long id, @RequestBody Proyecto proyecto) {
        return ResponseEntity.ok(proyectoService.actualizar(id, proyecto));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        proyectoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

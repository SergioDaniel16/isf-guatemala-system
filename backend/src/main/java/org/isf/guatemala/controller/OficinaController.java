package org.isf.guatemala.controller;

import org.isf.guatemala.model.Oficina;
import org.isf.guatemala.service.OficinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/oficinas")
@CrossOrigin(origins = "*")
public class OficinaController {
    
    @Autowired
    private OficinaService oficinaService;
    
    @GetMapping
    public ResponseEntity<List<Oficina>> obtenerTodas() {
        return ResponseEntity.ok(oficinaService.obtenerTodas());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Oficina> obtenerPorId(@PathVariable Long id) {
        return oficinaService.obtenerPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Oficina> crear(@RequestBody Oficina oficina) {
        return ResponseEntity.ok(oficinaService.crear(oficina));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Oficina> actualizar(@PathVariable Long id, @RequestBody Oficina oficina) {
        return ResponseEntity.ok(oficinaService.actualizar(id, oficina));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        oficinaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

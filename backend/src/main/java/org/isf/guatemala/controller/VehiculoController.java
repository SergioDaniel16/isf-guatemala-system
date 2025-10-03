package org.isf.guatemala.controller;

import org.isf.guatemala.model.Vehiculo;
import org.isf.guatemala.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
@CrossOrigin(origins = "*")
public class VehiculoController {
    
    @Autowired
    private VehiculoService vehiculoService;
    
    @GetMapping
    public ResponseEntity<List<Vehiculo>> obtenerTodos() {
        return ResponseEntity.ok(vehiculoService.obtenerTodos());
    }
    
    @GetMapping("/activos")
    public ResponseEntity<List<Vehiculo>> obtenerActivos() {
        return ResponseEntity.ok(vehiculoService.obtenerActivos());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Vehiculo> obtenerPorId(@PathVariable Long id) {
        return vehiculoService.obtenerPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Vehiculo> crear(@RequestBody Vehiculo vehiculo) {
        return ResponseEntity.ok(vehiculoService.crear(vehiculo));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Vehiculo> actualizar(@PathVariable Long id, @RequestBody Vehiculo vehiculo) {
        return ResponseEntity.ok(vehiculoService.actualizar(id, vehiculo));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        vehiculoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

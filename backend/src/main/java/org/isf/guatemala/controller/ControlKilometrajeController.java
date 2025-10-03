package org.isf.guatemala.controller;

import org.isf.guatemala.model.ControlKilometraje;
import org.isf.guatemala.service.ControlKilometrajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/control-kilometraje")
@CrossOrigin(origins = "*")
public class ControlKilometrajeController {
    
    @Autowired
    private ControlKilometrajeService controlKilometrajeService;
    
    @GetMapping
    public ResponseEntity<List<ControlKilometraje>> obtenerTodos() {
        return ResponseEntity.ok(controlKilometrajeService.obtenerTodos());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ControlKilometraje> obtenerPorId(@PathVariable Long id) {
        return controlKilometrajeService.obtenerPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/activos")
    public ResponseEntity<List<ControlKilometraje>> obtenerViajesActivos() {
        return ResponseEntity.ok(controlKilometrajeService.obtenerViajesActivos());
    }
    
    // Iniciar un viaje
    @PostMapping("/iniciar")
    public ResponseEntity<ControlKilometraje> iniciarViaje(@RequestBody ControlKilometraje controlKilometraje) {
        return ResponseEntity.ok(controlKilometrajeService.iniciarViaje(controlKilometraje));
    }
    
    // Cerrar un viaje
    @PutMapping("/cerrar/{id}")
    public ResponseEntity<ControlKilometraje> cerrarViaje(
        @PathVariable Long id,
        @RequestBody Map<String, Object> payload
    ) {
        BigDecimal millajeEntrada = new BigDecimal(payload.get("millajeEntrada").toString());
        String fotoFinal = payload.get("fotoFinal").toString();
        
        return ResponseEntity.ok(
            controlKilometrajeService.cerrarViaje(id, millajeEntrada, fotoFinal));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ControlKilometraje> actualizar(
        @PathVariable Long id, 
        @RequestBody ControlKilometraje controlKilometraje
    ) {
        return ResponseEntity.ok(controlKilometrajeService.actualizar(id, controlKilometraje));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        controlKilometrajeService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    
    // Endpoints para reportes
    
    @GetMapping("/proyecto/{proyectoId}")
    public ResponseEntity<List<ControlKilometraje>> obtenerPorProyecto(@PathVariable Long proyectoId) {
        return ResponseEntity.ok(controlKilometrajeService.obtenerPorProyecto(proyectoId));
    }
    
    @GetMapping("/vehiculo/{vehiculoId}")
    public ResponseEntity<List<ControlKilometraje>> obtenerPorVehiculo(@PathVariable Long vehiculoId) {
        return ResponseEntity.ok(controlKilometrajeService.obtenerPorVehiculo(vehiculoId));
    }
    
    @GetMapping("/rango-fechas")
    public ResponseEntity<List<ControlKilometraje>> obtenerPorRangoFechas(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin
    ) {
        return ResponseEntity.ok(controlKilometrajeService.obtenerPorRangoFechas(fechaInicio, fechaFin));
    }
}

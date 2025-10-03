package org.isf.guatemala.controller;

import org.isf.guatemala.model.RegistroHoras;
import org.isf.guatemala.service.RegistroHorasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/registro-horas")
@CrossOrigin(origins = "*")
public class RegistroHorasController {
    
    @Autowired
    private RegistroHorasService registroHorasService;
    
    @GetMapping
    public ResponseEntity<List<RegistroHoras>> obtenerTodos() {
        return ResponseEntity.ok(registroHorasService.obtenerTodos());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<RegistroHoras> obtenerPorId(@PathVariable Long id) {
        return registroHorasService.obtenerPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<RegistroHoras> crear(@RequestBody RegistroHoras registroHoras) {
        return ResponseEntity.ok(registroHorasService.crear(registroHoras));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<RegistroHoras> actualizar(@PathVariable Long id, @RequestBody RegistroHoras registroHoras) {
        return ResponseEntity.ok(registroHorasService.actualizar(id, registroHoras));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        registroHorasService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    
    // Endpoints para reportes y filtros
    
    @GetMapping("/proyecto/{proyectoId}")
    public ResponseEntity<List<RegistroHoras>> obtenerPorProyecto(@PathVariable Long proyectoId) {
        return ResponseEntity.ok(registroHorasService.obtenerPorProyecto(proyectoId));
    }
    
    @GetMapping("/empleado/{empleadoId}")
    public ResponseEntity<List<RegistroHoras>> obtenerPorEmpleado(@PathVariable Long empleadoId) {
        return ResponseEntity.ok(registroHorasService.obtenerPorEmpleado(empleadoId));
    }
    
    @GetMapping("/rango-fechas")
    public ResponseEntity<List<RegistroHoras>> obtenerPorRangoFechas(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin
    ) {
        return ResponseEntity.ok(registroHorasService.obtenerPorRangoFechas(fechaInicio, fechaFin));
    }
    
    @GetMapping("/filtros")
    public ResponseEntity<List<RegistroHoras>> obtenerConFiltros(
        @RequestParam(required = false) Long proyectoId,
        @RequestParam(required = false) Long empleadoId,
        @RequestParam(required = false) Boolean esCobrable,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin
    ) {
        return ResponseEntity.ok(registroHorasService.obtenerConFiltros(
            proyectoId, empleadoId, esCobrable, fechaInicio, fechaFin));
    }
}

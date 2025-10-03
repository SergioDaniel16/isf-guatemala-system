package org.isf.guatemala.controller;

import org.isf.guatemala.dto.request.RegistroHorasRequestDTO;
import org.isf.guatemala.dto.response.RegistroHorasResponseDTO;
import org.isf.guatemala.service.RegistroHorasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/registro-horas")
@Validated
public class RegistroHorasController {
    
    @Autowired
    private RegistroHorasService registroHorasService;
    
    @GetMapping
    public ResponseEntity<List<RegistroHorasResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(registroHorasService.obtenerTodos());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<RegistroHorasResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(registroHorasService.obtenerPorId(id));
    }
    
    @PostMapping
    public ResponseEntity<RegistroHorasResponseDTO> crear(@Valid @RequestBody RegistroHorasRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(registroHorasService.crear(dto));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<RegistroHorasResponseDTO> actualizar(
        @PathVariable Long id, 
        @Valid @RequestBody RegistroHorasRequestDTO dto
    ) {
        return ResponseEntity.ok(registroHorasService.actualizar(id, dto));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        registroHorasService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    
    // Endpoints para reportes
    
    @GetMapping("/proyecto/{proyectoId}")
    public ResponseEntity<List<RegistroHorasResponseDTO>> obtenerPorProyecto(@PathVariable Long proyectoId) {
        return ResponseEntity.ok(registroHorasService.obtenerPorProyecto(proyectoId));
    }
    
    @GetMapping("/empleado/{empleadoId}")
    public ResponseEntity<List<RegistroHorasResponseDTO>> obtenerPorEmpleado(@PathVariable Long empleadoId) {
        return ResponseEntity.ok(registroHorasService.obtenerPorEmpleado(empleadoId));
    }
    
    @GetMapping("/rango-fechas")
    public ResponseEntity<List<RegistroHorasResponseDTO>> obtenerPorRangoFechas(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin
    ) {
        return ResponseEntity.ok(registroHorasService.obtenerPorRangoFechas(fechaInicio, fechaFin));
    }
    
    @GetMapping("/filtros")
    public ResponseEntity<List<RegistroHorasResponseDTO>> obtenerConFiltros(
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

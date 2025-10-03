package org.isf.guatemala.controller;

import org.isf.guatemala.dto.request.ControlKilometrajeRequestDTO;
import org.isf.guatemala.dto.response.ControlKilometrajeResponseDTO;
import org.isf.guatemala.service.ControlKilometrajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/control-kilometraje")
@Validated
public class ControlKilometrajeController {
    
    @Autowired
    private ControlKilometrajeService controlKilometrajeService;
    
    @GetMapping
    public ResponseEntity<List<ControlKilometrajeResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(controlKilometrajeService.obtenerTodos());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ControlKilometrajeResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(controlKilometrajeService.obtenerPorId(id));
    }
    
    @GetMapping("/activos")
    public ResponseEntity<List<ControlKilometrajeResponseDTO>> obtenerViajesActivos() {
        return ResponseEntity.ok(controlKilometrajeService.obtenerViajesActivos());
    }
    
    // Iniciar un viaje
    @PostMapping("/iniciar")
    public ResponseEntity<ControlKilometrajeResponseDTO> iniciarViaje(
        @Valid @RequestBody ControlKilometrajeRequestDTO dto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(controlKilometrajeService.iniciarViaje(dto));
    }
    
    // Cerrar un viaje
    @PutMapping("/cerrar/{id}")
    public ResponseEntity<ControlKilometrajeResponseDTO> cerrarViaje(
        @PathVariable Long id,
        @RequestBody Map<String, Object> payload
    ) {
        BigDecimal millajeEntrada = new BigDecimal(payload.get("millajeEntrada").toString());
        String fotoFinal = payload.get("fotoFinal").toString();
        
        return ResponseEntity.ok(
            controlKilometrajeService.cerrarViaje(id, millajeEntrada, fotoFinal));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ControlKilometrajeResponseDTO> actualizar(
        @PathVariable Long id, 
        @Valid @RequestBody ControlKilometrajeRequestDTO dto
    ) {
        return ResponseEntity.ok(controlKilometrajeService.actualizar(id, dto));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        controlKilometrajeService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    
    // Endpoints para reportes
    
    @GetMapping("/proyecto/{proyectoId}")
    public ResponseEntity<List<ControlKilometrajeResponseDTO>> obtenerPorProyecto(@PathVariable Long proyectoId) {
        return ResponseEntity.ok(controlKilometrajeService.obtenerPorProyecto(proyectoId));
    }
    
    @GetMapping("/vehiculo/{vehiculoId}")
    public ResponseEntity<List<ControlKilometrajeResponseDTO>> obtenerPorVehiculo(@PathVariable Long vehiculoId) {
        return ResponseEntity.ok(controlKilometrajeService.obtenerPorVehiculo(vehiculoId));
    }
    
    @GetMapping("/rango-fechas")
    public ResponseEntity<List<ControlKilometrajeResponseDTO>> obtenerPorRangoFechas(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin
    ) {
        return ResponseEntity.ok(controlKilometrajeService.obtenerPorRangoFechas(fechaInicio, fechaFin));
    }
}

package org.isf.guatemala.controller;

import org.isf.guatemala.dto.request.VehiculoRequestDTO;
import org.isf.guatemala.dto.response.VehiculoResponseDTO;
import org.isf.guatemala.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
@Validated
public class VehiculoController {
    
    @Autowired
    private VehiculoService vehiculoService;
    
    @GetMapping
    public ResponseEntity<List<VehiculoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(vehiculoService.obtenerTodos());
    }
    
    @GetMapping("/activos")
    public ResponseEntity<List<VehiculoResponseDTO>> obtenerActivos() {
        return ResponseEntity.ok(vehiculoService.obtenerActivos());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<VehiculoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(vehiculoService.obtenerPorId(id));
    }
    
    @PostMapping
    public ResponseEntity<VehiculoResponseDTO> crear(@Valid @RequestBody VehiculoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vehiculoService.crear(dto));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<VehiculoResponseDTO> actualizar(
        @PathVariable Long id, 
        @Valid @RequestBody VehiculoRequestDTO dto
    ) {
        return ResponseEntity.ok(vehiculoService.actualizar(id, dto));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        vehiculoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

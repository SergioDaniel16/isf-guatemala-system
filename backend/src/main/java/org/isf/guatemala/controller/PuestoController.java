package org.isf.guatemala.controller;

import org.isf.guatemala.dto.request.PuestoRequestDTO;
import org.isf.guatemala.dto.response.PuestoResponseDTO;
import org.isf.guatemala.service.PuestoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/puestos")
@Validated
public class PuestoController {
    
    @Autowired
    private PuestoService puestoService;
    
    @GetMapping
    public ResponseEntity<List<PuestoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(puestoService.obtenerTodos());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PuestoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(puestoService.obtenerPorId(id));
    }
    
    @PostMapping
    public ResponseEntity<PuestoResponseDTO> crear(@Valid @RequestBody PuestoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(puestoService.crear(dto));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PuestoResponseDTO> actualizar(
        @PathVariable Long id, 
        @Valid @RequestBody PuestoRequestDTO dto
    ) {
        return ResponseEntity.ok(puestoService.actualizar(id, dto));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        puestoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

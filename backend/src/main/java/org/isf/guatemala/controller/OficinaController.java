package org.isf.guatemala.controller;

import org.isf.guatemala.dto.request.OficinaRequestDTO;
import org.isf.guatemala.dto.response.OficinaResponseDTO;
import org.isf.guatemala.service.OficinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/oficinas")
@Validated
public class OficinaController {
    
    @Autowired
    private OficinaService oficinaService;
    
    @GetMapping
    public ResponseEntity<List<OficinaResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(oficinaService.obtenerTodas());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<OficinaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(oficinaService.obtenerPorId(id));
    }
    
    @PostMapping
    public ResponseEntity<OficinaResponseDTO> crear(@Valid @RequestBody OficinaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(oficinaService.crear(dto));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<OficinaResponseDTO> actualizar(
        @PathVariable Long id, 
        @Valid @RequestBody OficinaRequestDTO dto
    ) {
        return ResponseEntity.ok(oficinaService.actualizar(id, dto));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        oficinaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

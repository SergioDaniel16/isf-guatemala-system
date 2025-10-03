package org.isf.guatemala.controller;

import org.isf.guatemala.dto.request.TareaRequestDTO;
import org.isf.guatemala.dto.response.TareaResponseDTO;
import org.isf.guatemala.service.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tareas")
@Validated
public class TareaController {
    
    @Autowired
    private TareaService tareaService;
    
    @GetMapping
    public ResponseEntity<List<TareaResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(tareaService.obtenerTodas());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TareaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tareaService.obtenerPorId(id));
    }
    
    @PostMapping
    public ResponseEntity<TareaResponseDTO> crear(@Valid @RequestBody TareaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tareaService.crear(dto));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<TareaResponseDTO> actualizar(
        @PathVariable Long id, 
        @Valid @RequestBody TareaRequestDTO dto
    ) {
        return ResponseEntity.ok(tareaService.actualizar(id, dto));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        tareaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

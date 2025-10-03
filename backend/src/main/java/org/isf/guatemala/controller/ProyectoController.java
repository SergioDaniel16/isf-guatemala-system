package org.isf.guatemala.controller;

import org.isf.guatemala.dto.request.ProyectoRequestDTO;
import org.isf.guatemala.dto.response.ProyectoResponseDTO;
import org.isf.guatemala.service.ProyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/proyectos")
@Validated
public class ProyectoController {
    
    @Autowired
    private ProyectoService proyectoService;
    
    @GetMapping
    public ResponseEntity<List<ProyectoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(proyectoService.obtenerTodos());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProyectoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(proyectoService.obtenerPorId(id));
    }
    
    @GetMapping("/oficina/{oficinaId}")
    public ResponseEntity<List<ProyectoResponseDTO>> obtenerPorOficina(@PathVariable Long oficinaId) {
        return ResponseEntity.ok(proyectoService.obtenerPorOficina(oficinaId));
    }
    
    @PostMapping
    public ResponseEntity<ProyectoResponseDTO> crear(@Valid @RequestBody ProyectoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(proyectoService.crear(dto));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ProyectoResponseDTO> actualizar(
        @PathVariable Long id, 
        @Valid @RequestBody ProyectoRequestDTO dto
    ) {
        return ResponseEntity.ok(proyectoService.actualizar(id, dto));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        proyectoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
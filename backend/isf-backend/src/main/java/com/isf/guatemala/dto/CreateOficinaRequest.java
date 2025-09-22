package com.isf.guatemala.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CreateOficinaRequest {
    
    @NotBlank(message = "El nombre de la oficina es obligatorio")
    @Size(max = 50, message = "El nombre no puede exceder 50 caracteres")
    private String nombre;
    
    @Size(max = 255, message = "La descripción no puede exceder 255 caracteres")
    private String descripcion;
    
    // Constructores
    public CreateOficinaRequest() {}
    
    public CreateOficinaRequest(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    
    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}

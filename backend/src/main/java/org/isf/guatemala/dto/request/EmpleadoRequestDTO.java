package org.isf.guatemala.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoRequestDTO {
    
    @NotBlank(message = "El primer nombre es obligatorio")
    @Size(max = 100, message = "El primer nombre no puede exceder 100 caracteres")
    private String primerNombre;
    
    @Size(max = 100, message = "El segundo nombre no puede exceder 100 caracteres")
    private String segundoNombre;
    
    @NotBlank(message = "El primer apellido es obligatorio")
    @Size(max = 100, message = "El primer apellido no puede exceder 100 caracteres")
    private String primerApellido;
    
    @Size(max = 100, message = "El segundo apellido no puede exceder 100 caracteres")
    private String segundoApellido;
    
    @NotBlank(message = "El contacto es obligatorio")
    @Pattern(regexp = "^[0-9]{8}$", message = "El contacto debe tener exactamente 8 dígitos")
    private String contacto;
    
    @NotNull(message = "El ID del puesto es obligatorio")
    private Long puestoId;
}

package org.isf.guatemala.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PuestoRequestDTO {
    
    @NotBlank(message = "El nombre del puesto es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;
    
    @Size(max = 500, message = "El detalle no puede exceder 500 caracteres")
    private String detalle;
    
    @NotNull(message = "El salario por hora es obligatorio")
    @Positive(message = "El salario debe ser mayor a 0")
    private BigDecimal salarioPorHora;
}

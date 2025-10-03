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
public class ProyectoRequestDTO {
    
    @NotBlank(message = "El código del proyecto es obligatorio")
    @Size(max = 50, message = "El código no puede exceder 50 caracteres")
    private String codigo;
    
    @NotBlank(message = "El nombre del proyecto es obligatorio")
    @Size(max = 200, message = "El nombre no puede exceder 200 caracteres")
    private String nombre;
    
    @NotNull(message = "El ID de la oficina es obligatorio")
    private Long oficinaId;
    
    @NotBlank(message = "El departamento es obligatorio")
    @Size(max = 100, message = "El departamento no puede exceder 100 caracteres")
    private String departamento;
    
    @NotBlank(message = "El municipio es obligatorio")
    @Size(max = 100, message = "El municipio no puede exceder 100 caracteres")
    private String municipio;
    
    @NotBlank(message = "El tipo de proyecto es obligatorio")
    private String tipoProyecto; // DIRECTO, CAPITULO, EXTERNO
    
    @NotNull(message = "El cambio de dólar es obligatorio")
    @Positive(message = "El cambio de dólar debe ser mayor a 0")
    private BigDecimal cambioDolar;
    
    @NotNull(message = "La tarifa base del vehículo es obligatoria")
    @Positive(message = "La tarifa base debe ser mayor a 0")
    private BigDecimal tarifaBaseVehiculo;
    
    @NotNull(message = "La tarifa por km extra es obligatoria")
    @Positive(message = "La tarifa por km extra debe ser mayor a 0")
    private BigDecimal tarifaKmExtra;
}

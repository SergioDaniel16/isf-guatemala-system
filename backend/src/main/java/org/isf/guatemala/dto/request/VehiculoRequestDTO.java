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
public class VehiculoRequestDTO {
    
    @NotBlank(message = "La marca es obligatoria")
    @Size(max = 50, message = "La marca no puede exceder 50 caracteres")
    private String marca;
    
    @NotBlank(message = "El modelo es obligatorio")
    @Size(max = 50, message = "El modelo no puede exceder 50 caracteres")
    private String modelo;
    
    @NotNull(message = "El año es obligatorio")
    @Positive(message = "El año debe ser válido")
    private Integer anio;
    
    @Size(max = 50, message = "El color no puede exceder 50 caracteres")
    private String color;
    
    @NotBlank(message = "La placa es obligatoria")
    @Size(max = 20, message = "La placa no puede exceder 20 caracteres")
    private String placa;
    
    @NotBlank(message = "El tipo de vehículo es obligatorio")
    @Size(max = 50, message = "El tipo de vehículo no puede exceder 50 caracteres")
    private String tipoVehiculo;
    
    @NotNull(message = "El odómetro actual es obligatorio")
    @Positive(message = "El odómetro debe ser mayor a 0")
    private BigDecimal odometroActual;
    
    @NotBlank(message = "El tipo de combustible es obligatorio")
    @Size(max = 50, message = "El tipo de combustible no puede exceder 50 caracteres")
    private String tipoCombustible;
    
    @NotBlank(message = "La transmisión es obligatoria")
    @Size(max = 50, message = "La transmisión no puede exceder 50 caracteres")
    private String transmision;
    
    @NotBlank(message = "La unidad de medida es obligatoria")
    private String unidadMedida; // MILLAS, KILOMETROS
    
    @NotNull(message = "Debe especificar si el vehículo está activo")
    private Boolean activo;
}

package org.isf.guatemala.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ControlKilometrajeRequestDTO {
    
    @NotNull(message = "El ID del vehículo es obligatorio")
    private Long vehiculoId;
    
    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;
    
    @NotNull(message = "El ID de la oficina es obligatorio")
    private Long oficinaId;
    
    @NotNull(message = "El ID del proyecto es obligatorio")
    private Long proyectoId;
    
    @Size(max = 200, message = "El destino no puede exceder 200 caracteres")
    private String destino;
    
    @NotNull(message = "El millaje de salida es obligatorio")
    @Positive(message = "El millaje de salida debe ser mayor a 0")
    private BigDecimal millajeSalida;
    
    @NotNull(message = "El ID del responsable es obligatorio")
    private Long responsableId;
    
    @Size(max = 500, message = "La URL de la foto inicial no puede exceder 500 caracteres")
    private String fotoInicial;
}

package org.isf.guatemala.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroHorasRequestDTO {
    
    @NotNull(message = "El ID de la oficina es obligatorio")
    private Long oficinaId;
    
    @NotNull(message = "El ID del proyecto es obligatorio")
    private Long proyectoId;
    
    @NotNull(message = "El ID de la tarea es obligatorio")
    private Long tareaId;
    
    @NotNull(message = "El ID del empleado es obligatorio")
    private Long empleadoId;
    
    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;
    
    @NotNull(message = "Las horas son obligatorias")
    @Min(value = 0, message = "Las horas deben ser entre 0 y 24")
    @Max(value = 24, message = "Las horas deben ser entre 0 y 24")
    private Integer horas;
    
    @NotNull(message = "Los minutos son obligatorios")
    @Min(value = 0, message = "Los minutos deben ser entre 0 y 59")
    @Max(value = 59, message = "Los minutos deben ser entre 0 y 59")
    private Integer minutos;
}

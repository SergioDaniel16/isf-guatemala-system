package org.isf.guatemala.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroHorasResponseDTO {
    private Long id;
    private OficinaResponseDTO oficina;
    private ProyectoResponseDTO proyecto;
    private TareaResponseDTO tarea;
    private EmpleadoResponseDTO empleado;
    private LocalDate fecha;
    private Integer horas;
    private Integer minutos;
    private BigDecimal horasTotales;
    private Boolean esCobrable;
    private BigDecimal costoTotal;
}

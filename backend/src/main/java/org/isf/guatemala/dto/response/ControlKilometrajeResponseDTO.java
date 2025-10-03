package org.isf.guatemala.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ControlKilometrajeResponseDTO {
    private Long id;
    private VehiculoResponseDTO vehiculo;
    private LocalDate fecha;
    private OficinaResponseDTO oficina;
    private ProyectoResponseDTO proyecto;
    private String destino;
    private BigDecimal millajeSalida;
    private BigDecimal millajeEntrada;
    private BigDecimal millasRecorrido;
    private BigDecimal km;
    private BigDecimal kmExtra;
    private EmpleadoResponseDTO responsable;
    private String fotoInicial;
    private String fotoFinal;
    private String estado;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
}

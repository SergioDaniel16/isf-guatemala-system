package org.isf.guatemala.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PuestoResponseDTO {
    private Long id;
    private String nombre;
    private String detalle;
    private BigDecimal salarioPorHora;
}

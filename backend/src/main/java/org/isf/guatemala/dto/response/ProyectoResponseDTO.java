package org.isf.guatemala.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProyectoResponseDTO {
    private Long id;
    private String codigo;
    private String nombre;
    private OficinaResponseDTO oficina;
    private String departamento;
    private String municipio;
    private String tipoProyecto;
    private BigDecimal cambioDolar;
    private BigDecimal tarifaBaseVehiculo;
    private BigDecimal tarifaKmExtra;
}

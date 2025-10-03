package org.isf.guatemala.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehiculoResponseDTO {
    private Long id;
    private String marca;
    private String modelo;
    private Integer anio;
    private String color;
    private String placa;
    private String tipoVehiculo;
    private BigDecimal odometroActual;
    private String tipoCombustible;
    private String transmision;
    private String unidadMedida;
    private Boolean activo;
    private String descripcionCompleta; // Calculado: "Toyota Hilux 2020 - P-123ABC"
}

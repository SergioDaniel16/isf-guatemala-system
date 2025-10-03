package org.isf.guatemala.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "vehiculos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehiculo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 50)
    private String marca;
    
    @Column(nullable = false, length = 50)
    private String modelo;
    
    @Column(nullable = false)
    private Integer anio;
    
    @Column(length = 50)
    private String color;
    
    @Column(nullable = false, unique = true, length = 20)
    private String placa;
    
    @Column(nullable = false, length = 50)
    private String tipoVehiculo;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal odometroActual;
    
    @Column(nullable = false, length = 50)
    private String tipoCombustible;
    
    @Column(nullable = false, length = 50)
    private String transmision;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UnidadMedida unidadMedida; // Millas o Kilómetros
    
    @Column(nullable = false)
    private Boolean activo;
    
    public enum UnidadMedida {
        MILLAS,
        KILOMETROS
    }
}

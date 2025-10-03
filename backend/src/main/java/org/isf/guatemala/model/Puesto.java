package org.isf.guatemala.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "puestos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Puesto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Column(length = 500)
    private String detalle;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal salarioPorHora;
}

package org.isf.guatemala.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "proyectos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Proyecto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 50)
    private String codigo;
    
    @Column(nullable = false, length = 200)
    private String nombre;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "oficina_id", nullable = false)
    private Oficina oficina;
    
    @Column(nullable = false, length = 100)
    private String departamento; // Ubicación
    
    @Column(nullable = false, length = 100)
    private String municipio;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoProyecto tipoProyecto; // Directo, Capitulo, Externo
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal cambioDolar;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal tarifaBaseVehiculo;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal tarifaKmExtra;
    
    public enum TipoProyecto {
        DIRECTO,
        CAPITULO,
        EXTERNO
    }
}

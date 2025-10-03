package org.isf.guatemala.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "registro_horas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroHoras {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "oficina_id", nullable = false)
    private Oficina oficina;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "proyecto_id", nullable = false)
    private Proyecto proyecto;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tarea_id", nullable = false)
    private Tarea tarea;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empleado_id", nullable = false)
    private Empleado empleado;
    
    @Column(nullable = false)
    private LocalDate fecha;
    
    @Column(nullable = false)
    private Integer horas; // 1-24
    
    @Column(nullable = false)
    private Integer minutos; // 1-60
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal horasTotales; // Calculado: horas + (minutos/60)
    
    @Column(nullable = false)
    private Boolean esCobrable; // Copiado de Tarea al momento de crear
    
    @Column(precision = 10, scale = 2)
    private BigDecimal costoTotal; // horasTotales * salarioPorHora del empleado
}

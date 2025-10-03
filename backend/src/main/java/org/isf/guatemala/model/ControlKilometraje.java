package org.isf.guatemala.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "control_kilometraje")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ControlKilometraje {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vehiculo_id", nullable = false)
    private Vehiculo vehiculo;
    
    @Column(nullable = false)
    private LocalDate fecha;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "oficina_id", nullable = false)
    private Oficina oficina;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "proyecto_id", nullable = false)
    private Proyecto proyecto;
    
    @Column(length = 200)
    private String destino;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal millajeSalida;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal millajeEntrada;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal millasRecorrido; // Calculado: millajeEntrada - millajeSalida
    
    @Column(precision = 10, scale = 2)
    private BigDecimal km; // Conversión automática si vehículo está en millas
    
    @Column(precision = 10, scale = 2)
    private BigDecimal kmExtra; // Si KM > 50
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "responsable_id", nullable = false)
    private Empleado responsable; // Quien empieza el viaje
    
    @Column(length = 500)
    private String fotoInicial; // URL o path de la foto
    
    @Column(length = 500)
    private String fotoFinal;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoViaje estado;
    
    @Column
    private LocalDateTime fechaInicio;
    
    @Column
    private LocalDateTime fechaFin;
    
    public enum EstadoViaje {
        ACTIVO,
        CERRADO
    }
}

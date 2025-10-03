package org.isf.guatemala.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;

@Entity
@Table(name = "empleados")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empleado {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String primerNombre;
    
    @Column(length = 100)
    private String segundoNombre;
    
    @Column(nullable = false, length = 100)
    private String primerApellido;
    
    @Column(length = 100)
    private String segundoApellido;
    
    @Column(nullable = false, length = 8)
    private String contacto; // Teléfono de 8 dígitos
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "puesto_id", nullable = false)
    private Puesto puesto;
}

package com.isf.guatemala.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "puestos")
public class Puesto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 100)
    private String nombre;
    
    @Column(length = 500)
    private String detalle;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal salarioPorHora;
    
    @Column(nullable = false)
    private Boolean activo = true;
    
    // Relaciones
    @OneToMany(mappedBy = "puesto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Empleado> empleados;
    
    // Constructores
    public Puesto() {}
    
    public Puesto(String nombre, String detalle, BigDecimal salarioPorHora) {
        this.nombre = nombre;
        this.detalle = detalle;
        this.salarioPorHora = salarioPorHora;
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDetalle() { return detalle; }
    public void setDetalle(String detalle) { this.detalle = detalle; }
    
    public BigDecimal getSalarioPorHora() { return salarioPorHora; }
    public void setSalarioPorHora(BigDecimal salarioPorHora) { this.salarioPorHora = salarioPorHora; }
    
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    
    public List<Empleado> getEmpleados() { return empleados; }
    public void setEmpleados(List<Empleado> empleados) { this.empleados = empleados; }
}

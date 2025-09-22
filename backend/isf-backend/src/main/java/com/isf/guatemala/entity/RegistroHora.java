package com.isf.guatemala.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "registro_horas")
public class RegistroHora {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private LocalDate fecha;
    
    @Column(nullable = false)
    private Integer horas; // 1-24
    
    @Column(nullable = false)
    private Integer minutos; // 1-60
    
    // Campos calculados
    @Column(nullable = false, precision = 10, scale = 4)
    private BigDecimal totalHorasDecimal; // horas + (minutos/60)
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal costoTotal; // totalHoras * salarioPorHora del empleado
    
    @Column(nullable = false)
    private Boolean esCobrable; // Se copia de la tarea al momento de crear el registro
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    
    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;
    
    // Relaciones
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oficina_id", nullable = false)
    private Oficina oficina;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proyecto_id", nullable = false)
    private Proyecto proyecto;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tarea_id", nullable = false)
    private Tarea tarea;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empleado_id", nullable = false)
    private Empleado empleado;
    
    // Constructores
    public RegistroHora() {
        this.fechaCreacion = LocalDateTime.now();
        this.fechaModificacion = LocalDateTime.now();
    }
    
    public RegistroHora(LocalDate fecha, Integer horas, Integer minutos, 
                       Oficina oficina, Proyecto proyecto, Tarea tarea, Empleado empleado) {
        this();
        this.fecha = fecha;
        this.horas = horas;
        this.minutos = minutos;
        this.oficina = oficina;
        this.proyecto = proyecto;
        this.tarea = tarea;
        this.empleado = empleado;
        this.esCobrable = tarea.getEsCobrable();
        calcularTotales();
    }
    
    // Métodos de cálculo
    public void calcularTotales() {
        // Calcular total de horas en decimal
        this.totalHorasDecimal = BigDecimal.valueOf(horas).add(
            BigDecimal.valueOf(minutos).divide(BigDecimal.valueOf(60), 4, BigDecimal.ROUND_HALF_UP)
        );
        
        // Calcular costo total si es cobrable
        if (esCobrable && empleado != null && empleado.getPuesto() != null) {
            this.costoTotal = totalHorasDecimal.multiply(empleado.getPuesto().getSalarioPorHora());
        } else {
            this.costoTotal = BigDecimal.ZERO;
        }
        
        this.fechaModificacion = LocalDateTime.now();
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    
    public Integer getHoras() { return horas; }
    public void setHoras(Integer horas) { 
        this.horas = horas;
        calcularTotales();
    }
    
    public Integer getMinutos() { return minutos; }
    public void setMinutos(Integer minutos) { 
        this.minutos = minutos;
        calcularTotales();
    }
    
    public BigDecimal getTotalHorasDecimal() { return totalHorasDecimal; }
    public void setTotalHorasDecimal(BigDecimal totalHorasDecimal) { this.totalHorasDecimal = totalHorasDecimal; }
    
    public BigDecimal getCostoTotal() { return costoTotal; }
    public void setCostoTotal(BigDecimal costoTotal) { this.costoTotal = costoTotal; }
    
    public Boolean getEsCobrable() { return esCobrable; }
    public void setEsCobrable(Boolean esCobrable) { this.esCobrable = esCobrable; }
    
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    
    public LocalDateTime getFechaModificacion() { return fechaModificacion; }
    public void setFechaModificacion(LocalDateTime fechaModificacion) { this.fechaModificacion = fechaModificacion; }
    
    public Oficina getOficina() { return oficina; }
    public void setOficina(Oficina oficina) { this.oficina = oficina; }
    
    public Proyecto getProyecto() { return proyecto; }
    public void setProyecto(Proyecto proyecto) { this.proyecto = proyecto; }
    
    public Tarea getTarea() { return tarea; }
    public void setTarea(Tarea tarea) { 
        this.tarea = tarea;
        if (tarea != null) {
            this.esCobrable = tarea.getEsCobrable();
            calcularTotales();
        }
    }
    
    public Empleado getEmpleado() { return empleado; }
    public void setEmpleado(Empleado empleado) { 
        this.empleado = empleado;
        calcularTotales();
    }
}
package com.isf.guatemala.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tareas")
public class Tarea {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 100)
    private String nombre;
    
    @Column(length = 500)
    private String definicion;
    
    @Column(nullable = false)
    private Boolean esCobrable = true; // Determina si se paga o no
    
    @Column(nullable = false)
    private Boolean activo = true;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    
    // Relaciones
    @OneToMany(mappedBy = "tarea", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RegistroHora> registrosHoras;
    
    // Constructores
    public Tarea() {
        this.fechaCreacion = LocalDateTime.now();
    }
    
    public Tarea(String nombre, String definicion, Boolean esCobrable) {
        this();
        this.nombre = nombre;
        this.definicion = definicion;
        this.esCobrable = esCobrable;
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDefinicion() { return definicion; }
    public void setDefinicion(String definicion) { this.definicion = definicion; }
    
    public Boolean getEsCobrable() { return esCobrable; }
    public void setEsCobrable(Boolean esCobrable) { this.esCobrable = esCobrable; }
    
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    
    public List<RegistroHora> getRegistrosHoras() { return registrosHoras; }
    public void setRegistrosHoras(List<RegistroHora> registrosHoras) { this.registrosHoras = registrosHoras; }
}
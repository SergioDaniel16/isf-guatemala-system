package com.isf.guatemala.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "proyectos")
public class Proyecto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 20)
    private String codigo;
    
    @Column(nullable = false, length = 200)
    private String nombre;
    
    @Column(nullable = false, length = 100)
    private String departamento;
    
    @Column(nullable = false, length = 100)
    private String municipio;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoProyecto tipo; // Directo, Capitulo, Externo
    
    @Column(nullable = false, precision = 10, scale = 4)
    private BigDecimal cambioDolar;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal tarifaBaseVehiculo;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal tarifaKmExtra;
    
    @Column(nullable = false)
    private Boolean activo = true;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    
    // Relaciones
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oficina_id", nullable = false)
    private Oficina oficina;
    
    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RegistroHora> registrosHoras;
    
    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ControlKilometraje> viajes;
    
    // Enum para tipo de proyecto
    public enum TipoProyecto {
        DIRECTO,
        CAPITULO,
        EXTERNO
    }
    
    // Constructores
    public Proyecto() {
        this.fechaCreacion = LocalDateTime.now();
    }
    
    public Proyecto(String codigo, String nombre, String departamento, String municipio, 
                   TipoProyecto tipo, BigDecimal cambioDolar, BigDecimal tarifaBaseVehiculo, 
                   BigDecimal tarifaKmExtra, Oficina oficina) {
        this();
        this.codigo = codigo;
        this.nombre = nombre;
        this.departamento = departamento;
        this.municipio = municipio;
        this.tipo = tipo;
        this.cambioDolar = cambioDolar;
        this.tarifaBaseVehiculo = tarifaBaseVehiculo;
        this.tarifaKmExtra = tarifaKmExtra;
        this.oficina = oficina;
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }
    
    public String getMunicipio() { return municipio; }
    public void setMunicipio(String municipio) { this.municipio = municipio; }
    
    public TipoProyecto getTipo() { return tipo; }
    public void setTipo(TipoProyecto tipo) { this.tipo = tipo; }
    
    public BigDecimal getCambioDolar() { return cambioDolar; }
    public void setCambioDolar(BigDecimal cambioDolar) { this.cambioDolar = cambioDolar; }
    
    public BigDecimal getTarifaBaseVehiculo() { return tarifaBaseVehiculo; }
    public void setTarifaBaseVehiculo(BigDecimal tarifaBaseVehiculo) { this.tarifaBaseVehiculo = tarifaBaseVehiculo; }
    
    public BigDecimal getTarifaKmExtra() { return tarifaKmExtra; }
    public void setTarifaKmExtra(BigDecimal tarifaKmExtra) { this.tarifaKmExtra = tarifaKmExtra; }
    
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    
    public Oficina getOficina() { return oficina; }
    public void setOficina(Oficina oficina) { this.oficina = oficina; }
    
    public List<RegistroHora> getRegistrosHoras() { return registrosHoras; }
    public void setRegistrosHoras(List<RegistroHora> registrosHoras) { this.registrosHoras = registrosHoras; }
    
    public List<ControlKilometraje> getViajes() { return viajes; }
    public void setViajes(List<ControlKilometraje> viajes) { this.viajes = viajes; }
}
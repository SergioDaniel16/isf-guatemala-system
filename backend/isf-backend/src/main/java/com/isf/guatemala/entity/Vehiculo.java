package com.isf.guatemala.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "vehiculos")
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
    
    @Column(nullable = false, length = 30)
    private String color;
    
    @Column(nullable = false, unique = true, length = 10)
    private String placa;
    
    @Column(nullable = false, length = 50)
    private String tipoVehiculo;
    
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal odometroActual;
    
    @Column(nullable = false, length = 20)
    private String tipoCombustible;
    
    @Column(nullable = false, length = 20)
    private String transmision;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UnidadMedida unidadMedida; // KILOMETROS o MILLAS
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoVehiculo estado = EstadoVehiculo.ACTIVO;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    
    // Relaciones
    @OneToMany(mappedBy = "vehiculo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ControlKilometraje> viajes;
    
    // Enums
    public enum UnidadMedida {
        KILOMETROS,
        MILLAS
    }
    
    public enum EstadoVehiculo {
        ACTIVO,
        INACTIVO,
        MANTENIMIENTO
    }
    
    // Constructores
    public Vehiculo() {
        this.fechaCreacion = LocalDateTime.now();
    }
    
    public Vehiculo(String marca, String modelo, Integer anio, String color, String placa, 
                   String tipoVehiculo, BigDecimal odometroActual, String tipoCombustible, 
                   String transmision, UnidadMedida unidadMedida) {
        this();
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.color = color;
        this.placa = placa;
        this.tipoVehiculo = tipoVehiculo;
        this.odometroActual = odometroActual;
        this.tipoCombustible = tipoCombustible;
        this.transmision = transmision;
        this.unidadMedida = unidadMedida;
    }
    
    // Método para obtener descripción completa del vehículo
    public String getDescripcionCompleta() {
        return String.format("%s %s %d - %s", marca, modelo, anio, placa);
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    
    public Integer getAnio() { return anio; }
    public void setAnio(Integer anio) { this.anio = anio; }
    
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    
    public String getTipoVehiculo() { return tipoVehiculo; }
    public void setTipoVehiculo(String tipoVehiculo) { this.tipoVehiculo = tipoVehiculo; }
    
    public BigDecimal getOdometroActual() { return odometroActual; }
    public void setOdometroActual(BigDecimal odometroActual) { this.odometroActual = odometroActual; }
    
    public String getTipoCombustible() { return tipoCombustible; }
    public void setTipoCombustible(String tipoCombustible) { this.tipoCombustible = tipoCombustible; }
    
    public String getTransmision() { return transmision; }
    public void setTransmision(String transmision) { this.transmision = transmision; }
    
    public UnidadMedida getUnidadMedida() { return unidadMedida; }
    public void setUnidadMedida(UnidadMedida unidadMedida) { this.unidadMedida = unidadMedida; }
    
    public EstadoVehiculo getEstado() { return estado; }
    public void setEstado(EstadoVehiculo estado) { this.estado = estado; }
    
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    
    public List<ControlKilometraje> getViajes() { return viajes; }
    public void setViajes(List<ControlKilometraje> viajes) { this.viajes = viajes; }
}
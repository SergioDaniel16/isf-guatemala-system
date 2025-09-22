package com.isf.guatemala.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "control_kilometraje")
public class ControlKilometraje {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idViaje;
    
    @Column(nullable = false)
    private LocalDate fecha;
    
    @Column(length = 255)
    private String destino;
    
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal millajeInicial;
    
    @Column(precision = 12, scale = 2)
    private BigDecimal millajeEgreso; // Se llena cuando se cierra el viaje
    
    // Campos calculados
    @Column(precision = 12, scale = 2)
    private BigDecimal millasRecorrido; // millajeEgreso - millajeInicial
    
    @Column(precision = 12, scale = 2)
    private BigDecimal kilometrosRecorrido; // Conversión automática si el vehículo está en millas
    
    @Column(precision = 10, scale = 2)
    private BigDecimal kmExtra; // Si KM > 50, entonces (KM - 50)
    
    @Column(precision = 10, scale = 2)
    private BigDecimal costoBase; // Tarifa base del proyecto
    
    @Column(precision = 10, scale = 2)
    private BigDecimal costoKmExtra; // kmExtra * tarifaKmExtra del proyecto
    
    @Column(precision = 10, scale = 2)
    private BigDecimal costoTotalDolares; // costoBase + costoKmExtra
    
    @Column(precision = 10, scale = 2)
    private BigDecimal costoTotalQuetzales; // costoTotalDolares * cambioDolar del proyecto
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoViaje estado = EstadoViaje.ACTIVO;
    
    @Column(name = "foto_inicial_url", length = 500)
    private String fotoInicialUrl; // URL en S3
    
    @Column(name = "foto_final_url", length = 500)
    private String fotoFinalUrl; // URL en S3
    
    @Column(name = "fecha_inicio")
    private LocalDateTime fechaInicio;
    
    @Column(name = "fecha_cierre")
    private LocalDateTime fechaCierre;
    
    // Relaciones
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehiculo_id", nullable = false)
    private Vehiculo vehiculo;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oficina_id", nullable = false)
    private Oficina oficina;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proyecto_id", nullable = false)
    private Proyecto proyecto;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsable_id", nullable = false)
    private Empleado responsable;
    
    // Enum para estado del viaje
    public enum EstadoViaje {
        ACTIVO,    // Viaje iniciado pero no cerrado
        CERRADO,   // Viaje completado
        CANCELADO  // Viaje cancelado
    }
    
    // Constructores
    public ControlKilometraje() {
        this.fechaInicio = LocalDateTime.now();
    }
    
    public ControlKilometraje(LocalDate fecha, String destino, BigDecimal millajeInicial,
                             Vehiculo vehiculo, Oficina oficina, Proyecto proyecto, Empleado responsable) {
        this();
        this.fecha = fecha;
        this.destino = destino;
        this.millajeInicial = millajeInicial;
        this.vehiculo = vehiculo;
        this.oficina = oficina;
        this.proyecto = proyecto;
        this.responsable = responsable;
        this.costoBase = proyecto.getTarifaBaseVehiculo();
    }
    
    // Métodos de cálculo
    public void cerrarViaje(BigDecimal millajeEgreso) {
        this.millajeEgreso = millajeEgreso;
        this.fechaCierre = LocalDateTime.now();
        this.estado = EstadoViaje.CERRADO;
        calcularTotales();
    }
    
    private void calcularTotales() {
        if (millajeEgreso != null && millajeInicial != null) {
            // Calcular millas recorrido
            this.millasRecorrido = millajeEgreso.subtract(millajeInicial);
            
            // Convertir a kilómetros si es necesario
            if (vehiculo.getUnidadMedida() == Vehiculo.UnidadMedida.MILLAS) {
                this.kilometrosRecorrido = millasRecorrido.multiply(new BigDecimal("1.609344"));
            } else {
                this.kilometrosRecorrido = millasRecorrido;
            }
            
            // Calcular KM extra si excede 50 km
            BigDecimal cincuentaKm = new BigDecimal("50");
            if (kilometrosRecorrido.compareTo(cincuentaKm) > 0) {
                this.kmExtra = kilometrosRecorrido.subtract(cincuentaKm);
                this.costoKmExtra = kmExtra.multiply(proyecto.getTarifaKmExtra());
            } else {
                this.kmExtra = BigDecimal.ZERO;
                this.costoKmExtra = BigDecimal.ZERO;
            }
            
            // Calcular costos totales
            this.costoTotalDolares = costoBase.add(costoKmExtra);
            this.costoTotalQuetzales = costoTotalDolares.multiply(proyecto.getCambioDolar());
        }
    }
    
    // Getters y Setters
    public Long getIdViaje() { return idViaje; }
    public void setIdViaje(Long idViaje) { this.idViaje = idViaje; }
    
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    
    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }
    
    public BigDecimal getMillajeInicial() { return millajeInicial; }
    public void setMillajeInicial(BigDecimal millajeInicial) { this.millajeInicial = millajeInicial; }
    
    public BigDecimal getMillajeEgreso() { return millajeEgreso; }
    public void setMillajeEgreso(BigDecimal millajeEgreso) { 
        this.millajeEgreso = millajeEgreso;
        calcularTotales();
    }
    
    public BigDecimal getMillasRecorrido() { return millasRecorrido; }
    public void setMillasRecorrido(BigDecimal millasRecorrido) { this.millasRecorrido = millasRecorrido; }
    
    public BigDecimal getKilometrosRecorrido() { return kilometrosRecorrido; }
    public void setKilometrosRecorrido(BigDecimal kilometrosRecorrido) { this.kilometrosRecorrido = kilometrosRecorrido; }
    
    public BigDecimal getKmExtra() { return kmExtra; }
    public void setKmExtra(BigDecimal kmExtra) { this.kmExtra = kmExtra; }
    
    public BigDecimal getCostoBase() { return costoBase; }
    public void setCostoBase(BigDecimal costoBase) { this.costoBase = costoBase; }
    
    public BigDecimal getCostoKmExtra() { return costoKmExtra; }
    public void setCostoKmExtra(BigDecimal costoKmExtra) { this.costoKmExtra = costoKmExtra; }
    
    public BigDecimal getCostoTotalDolares() { return costoTotalDolares; }
    public void setCostoTotalDolares(BigDecimal costoTotalDolares) { this.costoTotalDolares = costoTotalDolares; }
    
    public BigDecimal getCostoTotalQuetzales() { return costoTotalQuetzales; }
    public void setCostoTotalQuetzales(BigDecimal costoTotalQuetzales) { this.costoTotalQuetzales = costoTotalQuetzales; }
    
    public EstadoViaje getEstado() { return estado; }
    public void setEstado(EstadoViaje estado) { this.estado = estado; }
    
    public String getFotoInicialUrl() { return fotoInicialUrl; }
    public void setFotoInicialUrl(String fotoInicialUrl) { this.fotoInicialUrl = fotoInicialUrl; }
    
    public String getFotoFinalUrl() { return fotoFinalUrl; }
    public void setFotoFinalUrl(String fotoFinalUrl) { this.fotoFinalUrl = fotoFinalUrl; }
    
    public LocalDateTime getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDateTime fechaInicio) { this.fechaInicio = fechaInicio; }
    
    public LocalDateTime getFechaCierre() { return fechaCierre; }
    public void setFechaCierre(LocalDateTime fechaCierre) { this.fechaCierre = fechaCierre; }
    
    public Vehiculo getVehiculo() { return vehiculo; }
    public void setVehiculo(Vehiculo vehiculo) { this.vehiculo = vehiculo; }
    
    public Oficina getOficina() { return oficina; }
    public void setOficina(Oficina oficina) { this.oficina = oficina; }
    
    public Proyecto getProyecto() { return proyecto; }
    public void setProyecto(Proyecto proyecto) { this.proyecto = proyecto; }
    
    public Empleado getResponsable() { return responsable; }
    public void setResponsable(Empleado responsable) { this.responsable = responsable; }
}
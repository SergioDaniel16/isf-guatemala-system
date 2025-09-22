package com.isf.guatemala.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "empleados")
public class Empleado {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 50)
    private String primerNombre;
    
    @Column(length = 50)
    private String segundoNombre;
    
    @Column(nullable = false, length = 50)
    private String primerApellido;
    
    @Column(length = 50)
    private String segundoApellido;
    
    @Column(nullable = false, length = 8)
    private String contacto; // Teléfono de 8 dígitos
    
    @Column(nullable = false)
    private Boolean activo = true;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    
    // Relaciones
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "puesto_id", nullable = false)
    private Puesto puesto;
    
    @OneToMany(mappedBy = "empleado", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RegistroHora> registrosHoras;
    
    @OneToMany(mappedBy = "responsable", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ControlKilometraje> viajesRealizados;
    
    // Constructores
    public Empleado() {
        this.fechaCreacion = LocalDateTime.now();
    }
    
    public Empleado(String primerNombre, String primerApellido, String contacto, Puesto puesto) {
        this();
        this.primerNombre = primerNombre;
        this.primerApellido = primerApellido;
        this.contacto = contacto;
        this.puesto = puesto;
    }
    
    // Método para obtener nombre completo
    public String getNombreCompleto() {
        StringBuilder nombre = new StringBuilder();
        nombre.append(primerNombre);
        if (segundoNombre != null && !segundoNombre.isEmpty()) {
            nombre.append(" ").append(segundoNombre);
        }
        nombre.append(" ").append(primerApellido);
        if (segundoApellido != null && !segundoApellido.isEmpty()) {
            nombre.append(" ").append(segundoApellido);
        }
        return nombre.toString();
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getPrimerNombre() { return primerNombre; }
    public void setPrimerNombre(String primerNombre) { this.primerNombre = primerNombre; }
    
    public String getSegundoNombre() { return segundoNombre; }
    public void setSegundoNombre(String segundoNombre) { this.segundoNombre = segundoNombre; }
    
    public String getPrimerApellido() { return primerApellido; }
    public void setPrimerApellido(String primerApellido) { this.primerApellido = primerApellido; }
    
    public String getSegundoApellido() { return segundoApellido; }
    public void setSegundoApellido(String segundoApellido) { this.segundoApellido = segundoApellido; }
    
    public String getContacto() { return contacto; }
    public void setContacto(String contacto) { this.contacto = contacto; }
    
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    
    public Puesto getPuesto() { return puesto; }
    public void setPuesto(Puesto puesto) { this.puesto = puesto; }
    
    public List<RegistroHora> getRegistrosHoras() { return registrosHoras; }
    public void setRegistrosHoras(List<RegistroHora> registrosHoras) { this.registrosHoras = registrosHoras; }
    
    public List<ControlKilometraje> getViajesRealizados() { return viajesRealizados; }
    public void setViajesRealizados(List<ControlKilometraje> viajesRealizados) { this.viajesRealizados = viajesRealizados; }
}
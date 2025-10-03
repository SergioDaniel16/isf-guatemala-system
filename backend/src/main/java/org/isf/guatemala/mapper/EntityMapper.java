package org.isf.guatemala.mapper;

import org.isf.guatemala.dto.request.*;
import org.isf.guatemala.dto.response.*;
import org.isf.guatemala.model.*;
import org.isf.guatemala.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntityMapper {
    
    @Autowired
    private OficinaRepository oficinaRepository;
    
    @Autowired
    private PuestoRepository puestoRepository;
    
    @Autowired
    private EmpleadoRepository empleadoRepository;
    
    @Autowired
    private TareaRepository tareaRepository;
    
    @Autowired
    private VehiculoRepository vehiculoRepository;
    
    @Autowired
    private ProyectoRepository proyectoRepository;
    
    // ==================== OFICINA ====================
    
    public OficinaResponseDTO toOficinaResponse(Oficina oficina) {
        if (oficina == null) return null;
        return new OficinaResponseDTO(
            oficina.getId(),
            oficina.getNombre(),
            oficina.getDescripcion()
        );
    }
    
    public Oficina toOficinaEntity(OficinaRequestDTO dto) {
        Oficina oficina = new Oficina();
        oficina.setNombre(dto.getNombre());
        oficina.setDescripcion(dto.getDescripcion());
        return oficina;
    }
    
    // ==================== PUESTO ====================
    
    public PuestoResponseDTO toPuestoResponse(Puesto puesto) {
        if (puesto == null) return null;
        return new PuestoResponseDTO(
            puesto.getId(),
            puesto.getNombre(),
            puesto.getDetalle(),
            puesto.getSalarioPorHora()
        );
    }
    
    public Puesto toPuestoEntity(PuestoRequestDTO dto) {
        Puesto puesto = new Puesto();
        puesto.setNombre(dto.getNombre());
        puesto.setDetalle(dto.getDetalle());
        puesto.setSalarioPorHora(dto.getSalarioPorHora());
        return puesto;
    }
    
    // ==================== EMPLEADO ====================
    
    public EmpleadoResponseDTO toEmpleadoResponse(Empleado empleado) {
        if (empleado == null) return null;
        
        String nombreCompleto = empleado.getPrimerNombre() + " " +
            (empleado.getSegundoNombre() != null ? empleado.getSegundoNombre() + " " : "") +
            empleado.getPrimerApellido() +
            (empleado.getSegundoApellido() != null ? " " + empleado.getSegundoApellido() : "");
        
        return new EmpleadoResponseDTO(
            empleado.getId(),
            empleado.getPrimerNombre(),
            empleado.getSegundoNombre(),
            empleado.getPrimerApellido(),
            empleado.getSegundoApellido(),
            empleado.getContacto(),
            toPuestoResponse(empleado.getPuesto()),
            nombreCompleto
        );
    }
    
    public Empleado toEmpleadoEntity(EmpleadoRequestDTO dto) {
        Empleado empleado = new Empleado();
        empleado.setPrimerNombre(dto.getPrimerNombre());
        empleado.setSegundoNombre(dto.getSegundoNombre());
        empleado.setPrimerApellido(dto.getPrimerApellido());
        empleado.setSegundoApellido(dto.getSegundoApellido());
        empleado.setContacto(dto.getContacto());
        
        Puesto puesto = puestoRepository.findById(dto.getPuestoId())
            .orElseThrow(() -> new RuntimeException("Puesto no encontrado"));
        empleado.setPuesto(puesto);
        
        return empleado;
    }
    
    // ==================== TAREA ====================
    
    public TareaResponseDTO toTareaResponse(Tarea tarea) {
        if (tarea == null) return null;
        return new TareaResponseDTO(
            tarea.getId(),
            tarea.getNombre(),
            tarea.getDefinicion(),
            tarea.getEsCobrable()
        );
    }
    
    public Tarea toTareaEntity(TareaRequestDTO dto) {
        Tarea tarea = new Tarea();
        tarea.setNombre(dto.getNombre());
        tarea.setDefinicion(dto.getDefinicion());
        tarea.setEsCobrable(dto.getEsCobrable());
        return tarea;
    }
    
    // ==================== VEHICULO ====================
    
    public VehiculoResponseDTO toVehiculoResponse(Vehiculo vehiculo) {
        if (vehiculo == null) return null;
        
        String descripcionCompleta = vehiculo.getMarca() + " " + 
            vehiculo.getModelo() + " " + 
            vehiculo.getAnio() + " - " + 
            vehiculo.getPlaca();
        
        return new VehiculoResponseDTO(
            vehiculo.getId(),
            vehiculo.getMarca(),
            vehiculo.getModelo(),
            vehiculo.getAnio(),
            vehiculo.getColor(),
            vehiculo.getPlaca(),
            vehiculo.getTipoVehiculo(),
            vehiculo.getOdometroActual(),
            vehiculo.getTipoCombustible(),
            vehiculo.getTransmision(),
            vehiculo.getUnidadMedida().name(),
            vehiculo.getActivo(),
            descripcionCompleta
        );
    }
    
    public Vehiculo toVehiculoEntity(VehiculoRequestDTO dto) {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setMarca(dto.getMarca());
        vehiculo.setModelo(dto.getModelo());
        vehiculo.setAnio(dto.getAnio());
        vehiculo.setColor(dto.getColor());
        vehiculo.setPlaca(dto.getPlaca());
        vehiculo.setTipoVehiculo(dto.getTipoVehiculo());
        vehiculo.setOdometroActual(dto.getOdometroActual());
        vehiculo.setTipoCombustible(dto.getTipoCombustible());
        vehiculo.setTransmision(dto.getTransmision());
        vehiculo.setUnidadMedida(Vehiculo.UnidadMedida.valueOf(dto.getUnidadMedida()));
        vehiculo.setActivo(dto.getActivo());
        return vehiculo;
    }
    
    // ==================== PROYECTO ====================
    
    public ProyectoResponseDTO toProyectoResponse(Proyecto proyecto) {
        if (proyecto == null) return null;
        return new ProyectoResponseDTO(
            proyecto.getId(),
            proyecto.getCodigo(),
            proyecto.getNombre(),
            toOficinaResponse(proyecto.getOficina()),
            proyecto.getDepartamento(),
            proyecto.getMunicipio(),
            proyecto.getTipoProyecto().name(),
            proyecto.getCambioDolar(),
            proyecto.getTarifaBaseVehiculo(),
            proyecto.getTarifaKmExtra()
        );
    }
    
    public Proyecto toProyectoEntity(ProyectoRequestDTO dto) {
        Proyecto proyecto = new Proyecto();
        proyecto.setCodigo(dto.getCodigo());
        proyecto.setNombre(dto.getNombre());
        proyecto.setDepartamento(dto.getDepartamento());
        proyecto.setMunicipio(dto.getMunicipio());
        proyecto.setTipoProyecto(Proyecto.TipoProyecto.valueOf(dto.getTipoProyecto()));
        proyecto.setCambioDolar(dto.getCambioDolar());
        proyecto.setTarifaBaseVehiculo(dto.getTarifaBaseVehiculo());
        proyecto.setTarifaKmExtra(dto.getTarifaKmExtra());
        
        Oficina oficina = oficinaRepository.findById(dto.getOficinaId())
            .orElseThrow(() -> new RuntimeException("Oficina no encontrada"));
        proyecto.setOficina(oficina);
        
        return proyecto;
    }
    
    // ==================== REGISTRO HORAS ====================
    
    public RegistroHorasResponseDTO toRegistroHorasResponse(RegistroHoras registroHoras) {
        if (registroHoras == null) return null;
        return new RegistroHorasResponseDTO(
            registroHoras.getId(),
            toOficinaResponse(registroHoras.getOficina()),
            toProyectoResponse(registroHoras.getProyecto()),
            toTareaResponse(registroHoras.getTarea()),
            toEmpleadoResponse(registroHoras.getEmpleado()),
            registroHoras.getFecha(),
            registroHoras.getHoras(),
            registroHoras.getMinutos(),
            registroHoras.getHorasTotales(),
            registroHoras.getEsCobrable(),
            registroHoras.getCostoTotal()
        );
    }
    
    public RegistroHoras toRegistroHorasEntity(RegistroHorasRequestDTO dto) {
        RegistroHoras registroHoras = new RegistroHoras();
        registroHoras.setFecha(dto.getFecha());
        registroHoras.setHoras(dto.getHoras());
        registroHoras.setMinutos(dto.getMinutos());
        
        Oficina oficina = oficinaRepository.findById(dto.getOficinaId())
            .orElseThrow(() -> new RuntimeException("Oficina no encontrada"));
        registroHoras.setOficina(oficina);
        
        Proyecto proyecto = proyectoRepository.findById(dto.getProyectoId())
            .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
        registroHoras.setProyecto(proyecto);
        
        Tarea tarea = tareaRepository.findById(dto.getTareaId())
            .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
        registroHoras.setTarea(tarea);
        
        Empleado empleado = empleadoRepository.findById(dto.getEmpleadoId())
            .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        registroHoras.setEmpleado(empleado);
        
        return registroHoras;
    }
    
    // ==================== CONTROL KILOMETRAJE ====================
    
    public ControlKilometrajeResponseDTO toControlKilometrajeResponse(ControlKilometraje control) {
        if (control == null) return null;
        return new ControlKilometrajeResponseDTO(
            control.getId(),
            toVehiculoResponse(control.getVehiculo()),
            control.getFecha(),
            toOficinaResponse(control.getOficina()),
            toProyectoResponse(control.getProyecto()),
            control.getDestino(),
            control.getMillajeSalida(),
            control.getMillajeEntrada(),
            control.getMillasRecorrido(),
            control.getKm(),
            control.getKmExtra(),
            toEmpleadoResponse(control.getResponsable()),
            control.getFotoInicial(),
            control.getFotoFinal(),
            control.getEstado().name(),
            control.getFechaInicio(),
            control.getFechaFin()
        );
    }
    
    public ControlKilometraje toControlKilometrajeEntity(ControlKilometrajeRequestDTO dto) {
        ControlKilometraje control = new ControlKilometraje();
        control.setFecha(dto.getFecha());
        control.setDestino(dto.getDestino());
        control.setMillajeSalida(dto.getMillajeSalida());
        control.setFotoInicial(dto.getFotoInicial());
        
        Vehiculo vehiculo = vehiculoRepository.findById(dto.getVehiculoId())
            .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));
        control.setVehiculo(vehiculo);
        
        Oficina oficina = oficinaRepository.findById(dto.getOficinaId())
            .orElseThrow(() -> new RuntimeException("Oficina no encontrada"));
        control.setOficina(oficina);
        
        Proyecto proyecto = proyectoRepository.findById(dto.getProyectoId())
            .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
        control.setProyecto(proyecto);
        
        Empleado responsable = empleadoRepository.findById(dto.getResponsableId())
            .orElseThrow(() -> new RuntimeException("Responsable no encontrado"));
        control.setResponsable(responsable);
        
        return control;
    }
    
    // ==================== LIST CONVERSIONS ====================
    
    public List<OficinaResponseDTO> toOficinaResponseList(List<Oficina> oficinas) {
        return oficinas.stream().map(this::toOficinaResponse).collect(Collectors.toList());
    }
    
    public List<PuestoResponseDTO> toPuestoResponseList(List<Puesto> puestos) {
        return puestos.stream().map(this::toPuestoResponse).collect(Collectors.toList());
    }
    
    public List<EmpleadoResponseDTO> toEmpleadoResponseList(List<Empleado> empleados) {
        return empleados.stream().map(this::toEmpleadoResponse).collect(Collectors.toList());
    }
    
    public List<TareaResponseDTO> toTareaResponseList(List<Tarea> tareas) {
        return tareas.stream().map(this::toTareaResponse).collect(Collectors.toList());
    }
    
    public List<VehiculoResponseDTO> toVehiculoResponseList(List<Vehiculo> vehiculos) {
        return vehiculos.stream().map(this::toVehiculoResponse).collect(Collectors.toList());
    }
    
    public List<ProyectoResponseDTO> toProyectoResponseList(List<Proyecto> proyectos) {
        return proyectos.stream().map(this::toProyectoResponse).collect(Collectors.toList());
    }
    
    public List<RegistroHorasResponseDTO> toRegistroHorasResponseList(List<RegistroHoras> registros) {
        return registros.stream().map(this::toRegistroHorasResponse).collect(Collectors.toList());
    }
    
    public List<ControlKilometrajeResponseDTO> toControlKilometrajeResponseList(List<ControlKilometraje> controles) {
        return controles.stream().map(this::toControlKilometrajeResponse).collect(Collectors.toList());
    }
}

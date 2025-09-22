package com.isf.guatemala.controller;

import com.isf.guatemala.dto.ApiResponse;
import com.isf.guatemala.dto.CreateOficinaRequest;
import com.isf.guatemala.entity.*;
import com.isf.guatemala.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class AdministracionController {
    
    @Autowired
    private OficinaService oficinaService;
    
    @Autowired
    private PuestoService puestoService;
    
    @Autowired
    private EmpleadoService empleadoService;
    
    @Autowired
    private ProyectoService proyectoService;
    
    // ========== ENDPOINTS OFICINAS ==========
    
    @PostMapping("/oficinas")
    public ResponseEntity<ApiResponse<Oficina>> crearOficina(@Valid @RequestBody CreateOficinaRequest request) {
        try {
            Oficina oficina = oficinaService.crearOficina(request);
            return ResponseEntity.ok(ApiResponse.success("Oficina creada exitosamente", oficina));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
    
    @GetMapping("/oficinas")
    public ResponseEntity<ApiResponse<List<Oficina>>> obtenerOficinas(@RequestParam(defaultValue = "true") boolean soloActivas) {
        List<Oficina> oficinas = soloActivas ? 
            oficinaService.obtenerOficinasActivas() : 
            oficinaService.obtenerTodasLasOficinas();
        return ResponseEntity.ok(ApiResponse.success("Oficinas obtenidas exitosamente", oficinas));
    }
    
    @GetMapping("/oficinas/{id}")
    public ResponseEntity<ApiResponse<Oficina>> obtenerOficinaPorId(@PathVariable Long id) {
        Optional<Oficina> oficina = oficinaService.obtenerOficinaPorId(id);
        if (oficina.isPresent()) {
            return ResponseEntity.ok(ApiResponse.success("Oficina encontrada", oficina.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/oficinas/{id}")
    public ResponseEntity<ApiResponse<Oficina>> actualizarOficina(@PathVariable Long id, @Valid @RequestBody CreateOficinaRequest request) {
        try {
            Oficina oficina = oficinaService.actualizarOficina(id, request);
            return ResponseEntity.ok(ApiResponse.success("Oficina actualizada exitosamente", oficina));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
    
    @PutMapping("/oficinas/{id}/desactivar")
    public ResponseEntity<ApiResponse<Oficina>> desactivarOficina(@PathVariable Long id) {
        try {
            Oficina oficina = oficinaService.desactivarOficina(id);
            return ResponseEntity.ok(ApiResponse.success("Oficina desactivada exitosamente", oficina));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
    
    // ========== ENDPOINTS PUESTOS ==========
    
    @PostMapping("/puestos")
    public ResponseEntity<ApiResponse<Puesto>> crearPuesto(
            @RequestParam String nombre,
            @RequestParam(required = false) String detalle,
            @RequestParam BigDecimal salarioPorHora) {
        try {
            Puesto puesto = puestoService.crearPuesto(nombre, detalle, salarioPorHora);
            return ResponseEntity.ok(ApiResponse.success("Puesto creado exitosamente", puesto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
    
    @GetMapping("/puestos")
    public ResponseEntity<ApiResponse<List<Puesto>>> obtenerPuestos(@RequestParam(defaultValue = "true") boolean soloActivos) {
        List<Puesto> puestos = soloActivos ? 
            puestoService.obtenerPuestosActivos() : 
            puestoService.obtenerTodosLosPuestos();
        return ResponseEntity.ok(ApiResponse.success("Puestos obtenidos exitosamente", puestos));
    }
    
    @GetMapping("/puestos/{id}")
    public ResponseEntity<ApiResponse<Puesto>> obtenerPuestoPorId(@PathVariable Long id) {
        Optional<Puesto> puesto = puestoService.obtenerPuestoPorId(id);
        if (puesto.isPresent()) {
            return ResponseEntity.ok(ApiResponse.success("Puesto encontrado", puesto.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/puestos/{id}")
    public ResponseEntity<ApiResponse<Puesto>> actualizarPuesto(
            @PathVariable Long id,
            @RequestParam String nombre,
            @RequestParam(required = false) String detalle,
            @RequestParam BigDecimal salarioPorHora) {
        try {
            Puesto puesto = puestoService.actualizarPuesto(id, nombre, detalle, salarioPorHora);
            return ResponseEntity.ok(ApiResponse.success("Puesto actualizado exitosamente", puesto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
    
    @PutMapping("/puestos/{id}/desactivar")
    public ResponseEntity<ApiResponse<Puesto>> desactivarPuesto(@PathVariable Long id) {
        try {
            Puesto puesto = puestoService.desactivarPuesto(id);
            return ResponseEntity.ok(ApiResponse.success("Puesto desactivado exitosamente", puesto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
    
    // ========== ENDPOINTS EMPLEADOS ==========
    
    @PostMapping("/empleados")
    public ResponseEntity<ApiResponse<Empleado>> crearEmpleado(
            @RequestParam String primerNombre,
            @RequestParam(required = false) String segundoNombre,
            @RequestParam String primerApellido,
            @RequestParam(required = false) String segundoApellido,
            @RequestParam String contacto,
            @RequestParam Long puestoId) {
        try {
            Empleado empleado = empleadoService.crearEmpleado(primerNombre, segundoNombre, 
                                                            primerApellido, segundoApellido, 
                                                            contacto, puestoId);
            return ResponseEntity.ok(ApiResponse.success("Empleado creado exitosamente", empleado));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
    
    @GetMapping("/empleados")
    public ResponseEntity<ApiResponse<List<Empleado>>> obtenerEmpleados(@RequestParam(defaultValue = "true") boolean soloActivos) {
        List<Empleado> empleados = soloActivos ? 
            empleadoService.obtenerEmpleadosActivos() : 
            empleadoService.obtenerTodosLosEmpleados();
        return ResponseEntity.ok(ApiResponse.success("Empleados obtenidos exitosamente", empleados));
    }
    
    @GetMapping("/empleados/{id}")
    public ResponseEntity<ApiResponse<Empleado>> obtenerEmpleadoPorId(@PathVariable Long id) {
        Optional<Empleado> empleado = empleadoService.obtenerEmpleadoPorId(id);
        if (empleado.isPresent()) {
            return ResponseEntity.ok(ApiResponse.success("Empleado encontrado", empleado.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/empleados/buscar")
    public ResponseEntity<ApiResponse<List<Empleado>>> buscarEmpleados(@RequestParam String nombre) {
        List<Empleado> empleados = empleadoService.buscarEmpleadosPorNombre(nombre);
        return ResponseEntity.ok(ApiResponse.success("Búsqueda completada", empleados));
    }
    
    @GetMapping("/empleados/por-puesto/{puestoId}")
    public ResponseEntity<ApiResponse<List<Empleado>>> obtenerEmpleadosPorPuesto(@PathVariable Long puestoId) {
        List<Empleado> empleados = empleadoService.obtenerEmpleadosPorPuesto(puestoId);
        return ResponseEntity.ok(ApiResponse.success("Empleados por puesto obtenidos", empleados));
    }
    
    @PutMapping("/empleados/{id}")
    public ResponseEntity<ApiResponse<Empleado>> actualizarEmpleado(
            @PathVariable Long id,
            @RequestParam String primerNombre,
            @RequestParam(required = false) String segundoNombre,
            @RequestParam String primerApellido,
            @RequestParam(required = false) String segundoApellido,
            @RequestParam String contacto,
            @RequestParam Long puestoId) {
        try {
            Empleado empleado = empleadoService.actualizarEmpleado(id, primerNombre, segundoNombre, 
                                                                  primerApellido, segundoApellido, 
                                                                  contacto, puestoId);
            return ResponseEntity.ok(ApiResponse.success("Empleado actualizado exitosamente", empleado));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
    
    @PutMapping("/empleados/{id}/desactivar")
    public ResponseEntity<ApiResponse<Empleado>> desactivarEmpleado(@PathVariable Long id) {
        try {
            Empleado empleado = empleadoService.desactivarEmpleado(id);
            return ResponseEntity.ok(ApiResponse.success("Empleado desactivado exitosamente", empleado));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
    
    // ========== ENDPOINTS PROYECTOS ==========
    
    @PostMapping("/proyectos")
    public ResponseEntity<ApiResponse<Proyecto>> crearProyecto(
            @RequestParam String codigo,
            @RequestParam String nombre,
            @RequestParam String departamento,
            @RequestParam String municipio,
            @RequestParam Proyecto.TipoProyecto tipo,
            @RequestParam BigDecimal cambioDolar,
            @RequestParam BigDecimal tarifaBaseVehiculo,
            @RequestParam BigDecimal tarifaKmExtra,
            @RequestParam Long oficinaId) {
        try {
            Proyecto proyecto = proyectoService.crearProyecto(codigo, nombre, departamento, 
                                                            municipio, tipo, cambioDolar, 
                                                            tarifaBaseVehiculo, tarifaKmExtra, oficinaId);
            return ResponseEntity.ok(ApiResponse.success("Proyecto creado exitosamente", proyecto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
    
    @GetMapping("/proyectos")
    public ResponseEntity<ApiResponse<List<Proyecto>>> obtenerProyectos(@RequestParam(defaultValue = "true") boolean soloActivos) {
        List<Proyecto> proyectos = soloActivos ? 
            proyectoService.obtenerProyectosActivos() : 
            proyectoService.obtenerTodosLosProyectos();
        return ResponseEntity.ok(ApiResponse.success("Proyectos obtenidos exitosamente", proyectos));
    }
    
    @GetMapping("/proyectos/{id}")
    public ResponseEntity<ApiResponse<Proyecto>> obtenerProyectoPorId(@PathVariable Long id) {
        Optional<Proyecto> proyecto = proyectoService.obtenerProyectoPorId(id);
        if (proyecto.isPresent()) {
            return ResponseEntity.ok(ApiResponse.success("Proyecto encontrado", proyecto.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/proyectos/por-oficina/{oficinaId}")
    public ResponseEntity<ApiResponse<List<Proyecto>>> obtenerProyectosPorOficina(@PathVariable Long oficinaId) {
        List<Proyecto> proyectos = proyectoService.obtenerProyectosPorOficina(oficinaId);
        return ResponseEntity.ok(ApiResponse.success("Proyectos por oficina obtenidos", proyectos));
    }
    
    @PutMapping("/proyectos/{id}/desactivar")
    public ResponseEntity<ApiResponse<Proyecto>> desactivarProyecto(@PathVariable Long id) {
        try {
            Proyecto proyecto = proyectoService.desactivarProyecto(id);
            return ResponseEntity.ok(ApiResponse.success("Proyecto desactivado exitosamente", proyecto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}

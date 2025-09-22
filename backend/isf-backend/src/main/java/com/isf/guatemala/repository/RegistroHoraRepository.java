package com.isf.guatemala.repository;

import com.isf.guatemala.entity.RegistroHora;
import com.isf.guatemala.entity.Empleado;
import com.isf.guatemala.entity.Proyecto;
import com.isf.guatemala.entity.Oficina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RegistroHoraRepository extends JpaRepository<RegistroHora, Long> {
    
    // Buscar por empleado
    List<RegistroHora> findByEmpleado(Empleado empleado);
    
    // Buscar por proyecto
    List<RegistroHora> findByProyecto(Proyecto proyecto);
    
    // Buscar por oficina
    List<RegistroHora> findByOficina(Oficina oficina);
    
    // Buscar por rango de fechas
    List<RegistroHora> findByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin);
    
    // Buscar solo registros cobrables
    List<RegistroHora> findByEsCobrable(Boolean esCobrable);
    
    // CONSULTAS PARA REPORTES
    
    // Resumen por proyecto en rango de fechas
    @Query("SELECT r.proyecto.id, r.proyecto.nombre, " +
           "SUM(CASE WHEN r.esCobrable = true THEN r.totalHorasDecimal ELSE 0 END) as horasCobrables, " +
           "SUM(CASE WHEN r.esCobrable = false THEN r.totalHorasDecimal ELSE 0 END) as horasNoCobrables, " +
           "SUM(r.totalHorasDecimal) as totalHoras, " +
           "SUM(r.costoTotal) as costoTotal " +
           "FROM RegistroHora r " +
           "WHERE r.fecha BETWEEN :fechaInicio AND :fechaFin " +
           "GROUP BY r.proyecto.id, r.proyecto.nombre " +
           "ORDER BY r.proyecto.nombre")
    List<Object[]> getResumenPorProyecto(@Param("fechaInicio") LocalDate fechaInicio, 
                                        @Param("fechaFin") LocalDate fechaFin);
    
    // Resumen por empleado en rango de fechas
    @Query("SELECT r.empleado.id, " +
           "CONCAT(r.empleado.primerNombre, ' ', r.empleado.primerApellido) as nombreEmpleado, " +
           "SUM(CASE WHEN r.esCobrable = true THEN r.totalHorasDecimal ELSE 0 END) as horasCobrables, " +
           "SUM(CASE WHEN r.esCobrable = false THEN r.totalHorasDecimal ELSE 0 END) as horasNoCobrables, " +
           "SUM(r.totalHorasDecimal) as totalHoras, " +
           "SUM(r.costoTotal) as costoTotal " +
           "FROM RegistroHora r " +
           "WHERE r.fecha BETWEEN :fechaInicio AND :fechaFin " +
           "GROUP BY r.empleado.id, r.empleado.primerNombre, r.empleado.primerApellido " +
           "ORDER BY nombreEmpleado")
    List<Object[]> getResumenPorEmpleado(@Param("fechaInicio") LocalDate fechaInicio, 
                                        @Param("fechaFin") LocalDate fechaFin);
    
    // Resumen detallado (empleado + proyecto)
    @Query("SELECT r.empleado.id, " +
           "CONCAT(r.empleado.primerNombre, ' ', r.empleado.primerApellido) as nombreEmpleado, " +
           "r.proyecto.id, r.proyecto.nombre as nombreProyecto, " +
           "SUM(CASE WHEN r.esCobrable = true THEN r.totalHorasDecimal ELSE 0 END) as horasCobrables, " +
           "SUM(CASE WHEN r.esCobrable = false THEN r.totalHorasDecimal ELSE 0 END) as horasNoCobrables, " +
           "SUM(r.totalHorasDecimal) as totalHoras, " +
           "SUM(r.costoTotal) as costoTotal " +
           "FROM RegistroHora r " +
           "WHERE r.fecha BETWEEN :fechaInicio AND :fechaFin " +
           "GROUP BY r.empleado.id, r.empleado.primerNombre, r.empleado.primerApellido, " +
           "r.proyecto.id, r.proyecto.nombre " +
           "ORDER BY nombreEmpleado, nombreProyecto")
    List<Object[]> getResumenDetallado(@Param("fechaInicio") LocalDate fechaInicio, 
                                      @Param("fechaFin") LocalDate fechaFin);
    
    // CONSULTAS CON FILTROS ADICIONALES
    
    // Resumen por proyecto con filtros
    @Query("SELECT r.proyecto.id, r.proyecto.nombre, " +
           "SUM(CASE WHEN r.esCobrable = true THEN r.totalHorasDecimal ELSE 0 END) as horasCobrables, " +
           "SUM(CASE WHEN r.esCobrable = false THEN r.totalHorasDecimal ELSE 0 END) as horasNoCobrables, " +
           "SUM(r.totalHorasDecimal) as totalHoras, " +
           "SUM(r.costoTotal) as costoTotal " +
           "FROM RegistroHora r " +
           "WHERE r.fecha BETWEEN :fechaInicio AND :fechaFin " +
           "AND (:proyectoId IS NULL OR r.proyecto.id = :proyectoId) " +
           "AND (:empleadoId IS NULL OR r.empleado.id = :empleadoId) " +
           "AND (:esCobrable IS NULL OR r.esCobrable = :esCobrable) " +
           "GROUP BY r.proyecto.id, r.proyecto.nombre " +
           "ORDER BY r.proyecto.nombre")
    List<Object[]> getResumenPorProyectoConFiltros(@Param("fechaInicio") LocalDate fechaInicio, 
                                                  @Param("fechaFin") LocalDate fechaFin,
                                                  @Param("proyectoId") Long proyectoId,
                                                  @Param("empleadoId") Long empleadoId,
                                                  @Param("esCobrable") Boolean esCobrable);
    
    // Resumen por empleado con filtros
    @Query("SELECT r.empleado.id, " +
           "CONCAT(r.empleado.primerNombre, ' ', r.empleado.primerApellido) as nombreEmpleado, " +
           "SUM(CASE WHEN r.esCobrable = true THEN r.totalHorasDecimal ELSE 0 END) as horasCobrables, " +
           "SUM(CASE WHEN r.esCobrable = false THEN r.totalHorasDecimal ELSE 0 END) as horasNoCobrables, " +
           "SUM(r.totalHorasDecimal) as totalHoras, " +
           "SUM(r.costoTotal) as costoTotal " +
           "FROM RegistroHora r " +
           "WHERE r.fecha BETWEEN :fechaInicio AND :fechaFin " +
           "AND (:proyectoId IS NULL OR r.proyecto.id = :proyectoId) " +
           "AND (:empleadoId IS NULL OR r.empleado.id = :empleadoId) " +
           "AND (:esCobrable IS NULL OR r.esCobrable = :esCobrable) " +
           "GROUP BY r.empleado.id, r.empleado.primerNombre, r.empleado.primerApellido " +
           "ORDER BY nombreEmpleado")
    List<Object[]> getResumenPorEmpleadoConFiltros(@Param("fechaInicio") LocalDate fechaInicio, 
                                                  @Param("fechaFin") LocalDate fechaFin,
                                                  @Param("proyectoId") Long proyectoId,
                                                  @Param("empleadoId") Long empleadoId,
                                                  @Param("esCobrable") Boolean esCobrable);
    
    // Total general de costos en rango de fechas
    @Query("SELECT SUM(r.costoTotal) FROM RegistroHora r WHERE r.fecha BETWEEN :fechaInicio AND :fechaFin")
    BigDecimal getTotalCostosByDateRange(@Param("fechaInicio") LocalDate fechaInicio, 
                                        @Param("fechaFin") LocalDate fechaFin);
}

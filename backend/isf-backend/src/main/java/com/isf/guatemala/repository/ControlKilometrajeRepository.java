package com.isf.guatemala.repository;

import com.isf.guatemala.entity.ControlKilometraje;
import com.isf.guatemala.entity.Vehiculo;
import com.isf.guatemala.entity.Proyecto;
import com.isf.guatemala.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ControlKilometrajeRepository extends JpaRepository<ControlKilometraje, Long> {
    
    // Buscar viajes activos
    List<ControlKilometraje> findByEstado(ControlKilometraje.EstadoViaje estado);
    
    // Buscar viajes por vehículo
    List<ControlKilometraje> findByVehiculo(Vehiculo vehiculo);
    
    // Buscar viajes por proyecto
    List<ControlKilometraje> findByProyecto(Proyecto proyecto);
    
    // Buscar viajes por responsable
    List<ControlKilometraje> findByResponsable(Empleado responsable);
    
    // Buscar viajes por rango de fechas
    List<ControlKilometraje> findByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin);
    
    // Buscar viajes activos por vehículo (para verificar si un vehículo está en uso)
    List<ControlKilometraje> findByVehiculoAndEstado(Vehiculo vehiculo, ControlKilometraje.EstadoViaje estado);
    
    // CONSULTAS PARA REPORTES VEHICULARES
    
    // Reporte mensual por proyecto
    @Query("SELECT c.proyecto.id, c.proyecto.nombre, " +
           "COUNT(c) as cantidadViajes, " +
           "SUM(c.costoBase) as totalCostoBase, " +
           "SUM(c.kmExtra) as totalKmExtra, " +
           "SUM(c.costoKmExtra) as totalCostoKmExtra, " +
           "SUM(c.costoTotalDolares) as totalDolares, " +
           "SUM(c.costoTotalQuetzales) as totalQuetzales " +
           "FROM ControlKilometraje c " +
           "WHERE c.fecha BETWEEN :fechaInicio AND :fechaFin " +
           "AND c.estado = 'CERRADO' " +
           "GROUP BY c.proyecto.id, c.proyecto.nombre " +
           "ORDER BY c.proyecto.nombre")
    List<Object[]> getReporteMensualPorProyecto(@Param("fechaInicio") LocalDate fechaInicio, 
                                               @Param("fechaFin") LocalDate fechaFin);
    
    // Reporte de kilometraje por vehículo en un período
    @Query("SELECT c.vehiculo.id, " +
           "CONCAT(c.vehiculo.marca, ' ', c.vehiculo.modelo, ' - ', c.vehiculo.placa) as descripcionVehiculo, " +
           "MIN(c.millajeInicial) as millajeInicialPeriodo, " +
           "MAX(c.millajeEgreso) as millajeFinalPeriodo, " +
           "SUM(c.kilometrosRecorrido) as totalKilometros, " +
           "COUNT(c) as totalViajes " +
           "FROM ControlKilometraje c " +
           "WHERE c.fecha BETWEEN :fechaInicio AND :fechaFin " +
           "AND c.estado = 'CERRADO' " +
           "GROUP BY c.vehiculo.id, c.vehiculo.marca, c.vehiculo.modelo, c.vehiculo.placa " +
           "ORDER BY descripcionVehiculo")
    List<Object[]> getReporteKilometrajePorVehiculo(@Param("fechaInicio") LocalDate fechaInicio, 
                                                   @Param("fechaFin") LocalDate fechaFin);
    
    // Obtener viajes activos con información del vehículo y responsable
    @Query("SELECT c.idViaje, " +
           "CONCAT(c.vehiculo.marca, ' ', c.vehiculo.modelo) as descripcionVehiculo, " +
           "c.vehiculo.placa, " +
           "CONCAT(c.responsable.primerNombre, ' ', c.responsable.primerApellido) as nombreResponsable, " +
           "c.fecha, c.destino " +
           "FROM ControlKilometraje c " +
           "WHERE c.estado = 'ACTIVO' " +
           "ORDER BY c.fechaInicio DESC")
    List<Object[]> getViajesActivosDetalle();
    
    // Verificar si un vehículo tiene viajes activos
    @Query("SELECT COUNT(c) > 0 FROM ControlKilometraje c WHERE c.vehiculo.id = :vehiculoId AND c.estado = 'ACTIVO'")
    boolean hasVehiculoViajesActivos(@Param("vehiculoId") Long vehiculoId);
    
    // Obtener último millaje registrado de un vehículo
    @Query("SELECT MAX(c.millajeEgreso) FROM ControlKilometraje c " +
           "WHERE c.vehiculo.id = :vehiculoId AND c.estado = 'CERRADO' AND c.millajeEgreso IS NOT NULL")
    BigDecimal getUltimoMillajeVehiculo(@Param("vehiculoId") Long vehiculoId);
    
    // Reporte de costos por proyecto con filtros
    @Query("SELECT c.proyecto.id, c.proyecto.nombre, " +
           "COUNT(c) as cantidadViajes, " +
           "SUM(c.costoBase) as totalCostoBase, " +
           "SUM(c.kmExtra) as totalKmExtra, " +
           "SUM(c.costoKmExtra) as totalCostoKmExtra, " +
           "SUM(c.costoTotalDolares) as totalDolares, " +
           "SUM(c.costoTotalQuetzales) as totalQuetzales " +
           "FROM ControlKilometraje c " +
           "WHERE c.fecha BETWEEN :fechaInicio AND :fechaFin " +
           "AND c.estado = 'CERRADO' " +
           "AND (:proyectoId IS NULL OR c.proyecto.id = :proyectoId) " +
           "AND (:vehiculoId IS NULL OR c.vehiculo.id = :vehiculoId) " +
           "GROUP BY c.proyecto.id, c.proyecto.nombre " +
           "ORDER BY c.proyecto.nombre")
    List<Object[]> getReporteCostosPorProyectoConFiltros(@Param("fechaInicio") LocalDate fechaInicio, 
                                                        @Param("fechaFin") LocalDate fechaFin,
                                                        @Param("proyectoId") Long proyectoId,
                                                        @Param("vehiculoId") Long vehiculoId);
    
    // Total de costos en dólares para un período
    @Query("SELECT SUM(c.costoTotalDolares) FROM ControlKilometraje c " +
           "WHERE c.fecha BETWEEN :fechaInicio AND :fechaFin AND c.estado = 'CERRADO'")
    BigDecimal getTotalCostosDolaresByPeriod(@Param("fechaInicio") LocalDate fechaInicio, 
                                            @Param("fechaFin") LocalDate fechaFin);
    
    // Total de costos en quetzales para un período
    @Query("SELECT SUM(c.costoTotalQuetzales) FROM ControlKilometraje c " +
           "WHERE c.fecha BETWEEN :fechaInicio AND :fechaFin AND c.estado = 'CERRADO'")
    BigDecimal getTotalCostosQuetzalesByPeriod(@Param("fechaInicio") LocalDate fechaInicio, 
                                              @Param("fechaFin") LocalDate fechaFin);
}

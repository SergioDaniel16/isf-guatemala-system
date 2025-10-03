package org.isf.guatemala.repository;

import org.isf.guatemala.model.RegistroHoras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface RegistroHorasRepository extends JpaRepository<RegistroHoras, Long> {
    
    List<RegistroHoras> findByProyectoId(Long proyectoId);
    
    List<RegistroHoras> findByEmpleadoId(Long empleadoId);
    
    List<RegistroHoras> findByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin);
    
    List<RegistroHoras> findByProyectoIdAndFechaBetween(
        Long proyectoId, LocalDate fechaInicio, LocalDate fechaFin);
    
    List<RegistroHoras> findByEmpleadoIdAndFechaBetween(
        Long empleadoId, LocalDate fechaInicio, LocalDate fechaFin);
    
    List<RegistroHoras> findByEsCobrable(Boolean esCobrable);
    
    @Query("SELECT rh FROM RegistroHoras rh WHERE " +
           "(:proyectoId IS NULL OR rh.proyecto.id = :proyectoId) AND " +
           "(:empleadoId IS NULL OR rh.empleado.id = :empleadoId) AND " +
           "(:esCobrable IS NULL OR rh.esCobrable = :esCobrable) AND " +
           "rh.fecha BETWEEN :fechaInicio AND :fechaFin")
    List<RegistroHoras> findByFiltros(
        @Param("proyectoId") Long proyectoId,
        @Param("empleadoId") Long empleadoId,
        @Param("esCobrable") Boolean esCobrable,
        @Param("fechaInicio") LocalDate fechaInicio,
        @Param("fechaFin") LocalDate fechaFin);
}

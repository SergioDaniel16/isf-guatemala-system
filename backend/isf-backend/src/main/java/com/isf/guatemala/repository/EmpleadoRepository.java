package com.isf.guatemala.repository;

import com.isf.guatemala.entity.Empleado;
import com.isf.guatemala.entity.Puesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    
    // Buscar empleados activos
    List<Empleado> findByActivoTrue();
    
    // Buscar por nombre y apellido (búsqueda flexible)
    @Query("SELECT e FROM Empleado e WHERE e.activo = true AND " +
           "(LOWER(e.primerNombre) LIKE LOWER(CONCAT('%', :nombre, '%')) OR " +
           "LOWER(e.segundoNombre) LIKE LOWER(CONCAT('%', :nombre, '%')) OR " +
           "LOWER(e.primerApellido) LIKE LOWER(CONCAT('%', :nombre, '%')) OR " +
           "LOWER(e.segundoApellido) LIKE LOWER(CONCAT('%', :nombre, '%')))")
    List<Empleado> findByNombreContaining(@Param("nombre") String nombre);
    
    // Buscar por contacto (teléfono)
    Optional<Empleado> findByContacto(String contacto);
    
    // Buscar empleados por puesto
    List<Empleado> findByPuestoAndActivoTrue(Puesto puesto);
    
    // Verificar si existe por contacto
    boolean existsByContacto(String contacto);
    
    // Buscar empleados con registros de horas
    @Query("SELECT DISTINCT e FROM Empleado e JOIN e.registrosHoras r WHERE e.activo = true")
    List<Empleado> findEmpleadosWithHoursRecords();
    
    // Buscar empleados por puesto ID
    List<Empleado> findByPuestoIdAndActivoTrue(Long puestoId);
}

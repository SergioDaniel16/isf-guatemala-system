package com.isf.guatemala.repository;

import com.isf.guatemala.entity.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {
    
    // Buscar por nombre
    Optional<Tarea> findByNombre(String nombre);
    
    // Buscar tareas activas
    List<Tarea> findByActivoTrue();
    
    // Buscar tareas cobrables
    List<Tarea> findByEsCobrableAndActivoTrue(Boolean esCobrable);
    
    // Verificar si existe por nombre
    boolean existsByNombre(String nombre);
    
    // Buscar tareas por nombre (búsqueda flexible)
    @Query("SELECT t FROM Tarea t WHERE t.activo = true AND LOWER(t.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Tarea> findByNombreContaining(@Param("nombre") String nombre);
    
    // Buscar solo tareas cobrables activas
    @Query("SELECT t FROM Tarea t WHERE t.activo = true AND t.esCobrable = true ORDER BY t.nombre")
    List<Tarea> findCobrableTasksOrderByName();
    
    // Buscar solo tareas no cobrables activas
    @Query("SELECT t FROM Tarea t WHERE t.activo = true AND t.esCobrable = false ORDER BY t.nombre")
    List<Tarea> findNonCobrableTasksOrderByName();
}

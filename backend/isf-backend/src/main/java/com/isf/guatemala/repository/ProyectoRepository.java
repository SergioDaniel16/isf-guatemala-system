package com.isf.guatemala.repository;

import com.isf.guatemala.entity.Proyecto;
import com.isf.guatemala.entity.Oficina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {
    
    // Buscar por código
    Optional<Proyecto> findByCodigo(String codigo);
    
    // Buscar proyectos activos
    List<Proyecto> findByActivoTrue();
    
    // Buscar proyectos por oficina
    List<Proyecto> findByOficinaAndActivoTrue(Oficina oficina);
    
    // Buscar proyectos por oficina ID
    List<Proyecto> findByOficinaIdAndActivoTrue(Long oficinaId);
    
    // Verificar si existe por código
    boolean existsByCodigo(String codigo);
    
    // Buscar por tipo de proyecto
    List<Proyecto> findByTipoAndActivoTrue(Proyecto.TipoProyecto tipo);
    
    // Buscar proyectos por departamento
    List<Proyecto> findByDepartamentoAndActivoTrue(String departamento);
    
    // Buscar proyectos por nombre (búsqueda flexible)
    @Query("SELECT p FROM Proyecto p WHERE p.activo = true AND LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Proyecto> findByNombreContaining(@Param("nombre") String nombre);
    
    // Buscar proyectos con registros de horas
    @Query("SELECT DISTINCT p FROM Proyecto p JOIN p.registrosHoras r WHERE p.activo = true")
    List<Proyecto> findProyectosWithHoursRecords();
    
    // Buscar proyectos con viajes
    @Query("SELECT DISTINCT p FROM Proyecto p JOIN p.viajes v WHERE p.activo = true")
    List<Proyecto> findProyectosWithTrips();
}

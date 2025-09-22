package com.isf.guatemala.repository;

import com.isf.guatemala.entity.Oficina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface OficinaRepository extends JpaRepository<Oficina, Long> {
    
    // Buscar por nombre
    Optional<Oficina> findByNombre(String nombre);
    
    // Buscar oficinas activas
    List<Oficina> findByActivoTrue();
    
    // Verificar si existe por nombre (útil para validaciones)
    boolean existsByNombre(String nombre);
    
    // Buscar oficinas con proyectos activos
    @Query("SELECT DISTINCT o FROM Oficina o JOIN o.proyectos p WHERE p.activo = true AND o.activo = true")
    List<Oficina> findOficinasWithActiveProjects();
}

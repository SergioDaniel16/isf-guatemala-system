package com.isf.guatemala.repository;

import com.isf.guatemala.entity.Puesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface PuestoRepository extends JpaRepository<Puesto, Long> {
    
    // Buscar por nombre
    Optional<Puesto> findByNombre(String nombre);
    
    // Buscar puestos activos
    List<Puesto> findByActivoTrue();
    
    // Verificar si existe por nombre
    boolean existsByNombre(String nombre);
    
    // Buscar puestos por rango salarial
    List<Puesto> findBySalarioPorHoraBetweenAndActivoTrue(BigDecimal salarioMin, BigDecimal salarioMax);
    
    // Buscar puestos ordenados por salario
    @Query("SELECT p FROM Puesto p WHERE p.activo = true ORDER BY p.salarioPorHora DESC")
    List<Puesto> findActivePuestosOrderBySalaryDesc();
}

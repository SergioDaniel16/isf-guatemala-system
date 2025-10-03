package org.isf.guatemala.repository;

import org.isf.guatemala.model.ControlKilometraje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ControlKilometrajeRepository extends JpaRepository<ControlKilometraje, Long> {
    
    List<ControlKilometraje> findByEstado(ControlKilometraje.EstadoViaje estado);
    
    List<ControlKilometraje> findByVehiculoId(Long vehiculoId);
    
    List<ControlKilometraje> findByProyectoId(Long proyectoId);
    
    List<ControlKilometraje> findByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin);
    
    List<ControlKilometraje> findByProyectoIdAndFechaBetween(
        Long proyectoId, LocalDate fechaInicio, LocalDate fechaFin);
}

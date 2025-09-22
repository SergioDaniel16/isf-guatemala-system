package com.isf.guatemala.repository;

import com.isf.guatemala.entity.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
    
    // Buscar por placa
    Optional<Vehiculo> findByPlaca(String placa);
    
    // Buscar vehículos activos
    List<Vehiculo> findByEstado(Vehiculo.EstadoVehiculo estado);
    
    // Verificar si existe por placa
    boolean existsByPlaca(String placa);
    
    // Buscar por marca y modelo
    List<Vehiculo> findByMarcaAndModeloAndEstado(String marca, String modelo, Vehiculo.EstadoVehiculo estado);
    
    // Buscar vehículos disponibles (activos)
    @Query("SELECT v FROM Vehiculo v WHERE v.estado = 'ACTIVO' ORDER BY v.marca, v.modelo")
    List<Vehiculo> findAvailableVehicles();
    
    // Buscar vehículos por tipo
    List<Vehiculo> findByTipoVehiculoAndEstado(String tipoVehiculo, Vehiculo.EstadoVehiculo estado);
    
    // Buscar vehículos por año
    List<Vehiculo> findByAnioBetweenAndEstado(Integer anioInicio, Integer anioFin, Vehiculo.EstadoVehiculo estado);
    
    // Buscar vehículos con viajes activos
    @Query("SELECT DISTINCT v FROM Vehiculo v JOIN v.viajes viaje WHERE viaje.estado = 'ACTIVO'")
    List<Vehiculo> findVehiculosWithActiveTrips();
    
    // Buscar vehículos por descripción (marca, modelo, placa)
    @Query("SELECT v FROM Vehiculo v WHERE v.estado = 'ACTIVO' AND " +
           "(LOWER(v.marca) LIKE LOWER(CONCAT('%', :busqueda, '%')) OR " +
           "LOWER(v.modelo) LIKE LOWER(CONCAT('%', :busqueda, '%')) OR " +
           "LOWER(v.placa) LIKE LOWER(CONCAT('%', :busqueda, '%')))")
    List<Vehiculo> findByDescriptionContaining(@Param("busqueda") String busqueda);
}

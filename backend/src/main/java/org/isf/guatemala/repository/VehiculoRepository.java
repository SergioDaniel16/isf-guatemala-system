package org.isf.guatemala.repository;

import org.isf.guatemala.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
    Optional<Vehiculo> findByPlaca(String placa);
    List<Vehiculo> findByActivo(Boolean activo);
    boolean existsByPlaca(String placa);
}

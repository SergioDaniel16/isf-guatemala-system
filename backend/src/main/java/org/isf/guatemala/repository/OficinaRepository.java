package org.isf.guatemala.repository;

import org.isf.guatemala.model.Oficina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface OficinaRepository extends JpaRepository<Oficina, Long> {
    Optional<Oficina> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
}

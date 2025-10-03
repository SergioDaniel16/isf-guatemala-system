package org.isf.guatemala.repository;

import org.isf.guatemala.model.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {
    Optional<Proyecto> findByCodigo(String codigo);
    List<Proyecto> findByOficinaId(Long oficinaId);
    boolean existsByCodigo(String codigo);
}

package org.isf.guatemala.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "oficinas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Oficina {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 50)
    private String nombre; // Nacional, Xela, Joyabaj, Sololá
    
    @Column(length = 200)
    private String descripcion;
    
    @OneToMany(mappedBy = "oficina", cascade = CascadeType.ALL)
    private List<Proyecto> proyectos;
}

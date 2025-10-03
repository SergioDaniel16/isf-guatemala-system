package org.isf.guatemala.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TareaResponseDTO {
    private Long id;
    private String nombre;
    private String definicion;
    private Boolean esCobrable;
}

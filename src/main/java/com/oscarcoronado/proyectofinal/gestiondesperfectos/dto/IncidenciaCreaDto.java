package com.oscarcoronado.proyectofinal.gestiondesperfectos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncidenciaCreaDto {
	
	private String titulo;
    private String descripcion;

    private Long usuarioId;
    private Long estadoId;
    private Long aulaId; 

}

package com.oscarcoronado.proyectofinal.gestiondesperfectos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AulaDto {
	
	private Long id;
    private String codigo;
    private String descripcion;

}

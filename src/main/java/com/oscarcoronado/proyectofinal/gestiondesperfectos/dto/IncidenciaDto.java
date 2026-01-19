package com.oscarcoronado.proyectofinal.gestiondesperfectos.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncidenciaDto {
	
    private Long id;
    private String titulo;
    private String descripcion;

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    private Long usuarioId;
    private String usuarioUsername;

    private Long estadoId;
    private String estadoNombre;

    private Long aulaId;
    private String aulaCodigo;
	
}

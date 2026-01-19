package com.oscarcoronado.proyectofinal.gestiondesperfectos.servicio;

import java.util.List;

import com.oscarcoronado.proyectofinal.gestiondesperfectos.dto.IncidenciaCreaDto;
import com.oscarcoronado.proyectofinal.gestiondesperfectos.dto.IncidenciaDto;

public interface IncidenciaServicio {
	
	IncidenciaDto crear(IncidenciaCreaDto dto);

    List<IncidenciaDto> listAll();
    IncidenciaDto listById(Long id);

    List<IncidenciaDto> listByUsuario(Long usuarioId);

    IncidenciaDto cambiarEstado(Long incidenciaId, Long nuevoEstadoId);

    void borrarById(Long id);
    void borrarAll();

}

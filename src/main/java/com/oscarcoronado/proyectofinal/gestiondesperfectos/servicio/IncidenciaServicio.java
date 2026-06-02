package com.oscarcoronado.proyectofinal.gestiondesperfectos.servicio;

import java.util.List;

import com.oscarcoronado.proyectofinal.gestiondesperfectos.dto.IncidenciaCreaDto;
import com.oscarcoronado.proyectofinal.gestiondesperfectos.dto.IncidenciaDto;
import com.oscarcoronado.proyectofinal.gestiondesperfectos.dto.IncidenciaEditaDto;

public interface IncidenciaServicio {
	
	IncidenciaDto crear(IncidenciaCreaDto dto);

    List<IncidenciaDto> listAll();
    IncidenciaDto listById(Long id);

    List<IncidenciaDto> listByUsuario(Long usuarioId);

    IncidenciaDto cambiarEstado(Long incidenciaId, Long nuevoEstadoId);
    
    List<IncidenciaDto> listActivas();
    
    IncidenciaDto editar(Long id, IncidenciaEditaDto dto);

    void borrarById(Long id);
    void borrarAll();

}

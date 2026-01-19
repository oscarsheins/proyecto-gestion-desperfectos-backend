package com.oscarcoronado.proyectofinal.gestiondesperfectos.servicio;

import java.util.List;

import com.oscarcoronado.proyectofinal.gestiondesperfectos.dto.AulaDto;

public interface AulaServicio {
	
	List<AulaDto> listarAulas();
	AulaDto buscarAulaPorId(Long id);
	void eliminarTodosLasAulas();
	void eliminarAulaPorid(Long id);
	AulaDto crearAula(AulaDto aulaDto);

}

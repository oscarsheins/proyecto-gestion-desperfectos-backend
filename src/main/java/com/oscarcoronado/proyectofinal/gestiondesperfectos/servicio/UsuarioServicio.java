package com.oscarcoronado.proyectofinal.gestiondesperfectos.servicio;

import java.util.List;

import com.oscarcoronado.proyectofinal.gestiondesperfectos.dto.UsuarioDto;

public interface UsuarioServicio {
	
	UsuarioDto buscarUsuarioPorId(Long id);
	List<UsuarioDto> listarUsuarios();
	void eliminarTodosLosUsuarios();
	void eliminarUsuarioPorId(Long id);

}

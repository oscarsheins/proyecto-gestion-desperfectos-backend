package com.oscarcoronado.proyectofinal.gestiondesperfectos.servicio;

import java.util.List;

import com.oscarcoronado.proyectofinal.gestiondesperfectos.dto.UsuarioDto;
import com.oscarcoronado.proyectofinal.gestiondesperfectos.entidad.Usuario;

public interface UsuarioServicio {
	
	UsuarioDto buscarUsuarioPorId(Long id);
	List<UsuarioDto> listarUsuarios();
	void eliminarTodosLosUsuarios();
	void eliminarUsuarioPorId(Long id);
	Usuario login(String usuario, String password);

}

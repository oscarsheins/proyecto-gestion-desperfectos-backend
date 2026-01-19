package com.oscarcoronado.proyectofinal.gestiondesperfectos.servicioImp;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oscarcoronado.proyectofinal.gestiondesperfectos.dto.UsuarioDto;
import com.oscarcoronado.proyectofinal.gestiondesperfectos.entidad.Usuario;
import com.oscarcoronado.proyectofinal.gestiondesperfectos.repositorio.UsuarioRepositorio;
import com.oscarcoronado.proyectofinal.gestiondesperfectos.servicio.UsuarioServicio;

@Service
public class UsuarioServicioImp implements UsuarioServicio {
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public List<UsuarioDto> listarUsuarios() {
		List<Usuario> UsuariosModelo = usuarioRepositorio.findAll();
		return UsuariosModelo.stream().map(usuario -> modelMapper.map(usuario, UsuarioDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public UsuarioDto buscarUsuarioPorId(Long id) {
		Usuario usuario = usuarioRepositorio.findById(id).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
		return modelMapper.map(usuario, UsuarioDto.class);
	}

	@Override
	public void eliminarTodosLosUsuarios() {
		usuarioRepositorio.deleteAll();
		
	}

	@Override
	public void eliminarUsuarioPorId(Long id) {
		
	    if (!usuarioRepositorio.existsById(id)) {
	        throw new IllegalArgumentException("El Usuario con id " + id + " no existe");
	    }
		
		usuarioRepositorio.deleteById(id);
		
	}

}

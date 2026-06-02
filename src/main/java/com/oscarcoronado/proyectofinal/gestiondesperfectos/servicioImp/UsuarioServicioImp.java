package com.oscarcoronado.proyectofinal.gestiondesperfectos.servicioImp;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oscarcoronado.proyectofinal.gestiondesperfectos.dto.UsuarioCrearDto;
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
		Usuario usuario = usuarioRepositorio.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
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

	@Override
	public Usuario login(String usuario, String password) {

		Optional<Usuario> usuarioBD = usuarioRepositorio.findByUsername(usuario);

		if (usuarioBD.isPresent() && usuarioBD.get().getPassword().equals(password)) {

			return usuarioBD.get();
		}

		return null;
	}

	// Permite crear usuario esto lo podra hacer el usuario con rol de admin

	@Override
	public UsuarioCrearDto crearUsuario(UsuarioCrearDto usuarioCrearDto) {
		 if (usuarioCrearDto == null) {
		        throw new RuntimeException("Datos de usuario obligatorios");
		    }

		    if (usuarioCrearDto.getUsername() == null || usuarioCrearDto.getUsername().trim().isEmpty()) {
		        throw new RuntimeException("El username es obligatorio");
		    }

		    if (usuarioRepositorio.existsByUsername(usuarioCrearDto.getUsername())) {
		        throw new RuntimeException("Ya existe un usuario con ese username");
		    }

		    Usuario usuario = modelMapper.map(usuarioCrearDto, Usuario.class);
		    Usuario guardado = usuarioRepositorio.save(usuario);

		    return modelMapper.map(guardado, UsuarioCrearDto.class);
	}
	
	// Permite Actualizar los usuarios.
	@Override
	public UsuarioCrearDto editar(Long id, UsuarioCrearDto usuarioCrearDto) {
		 if (id == null) {
		        throw new RuntimeException("id es obligatorio");
		    }

		    if (usuarioCrearDto == null) {
		        throw new RuntimeException("Datos del usuario obligatorios");
		    }

		    if (usuarioCrearDto.getUsername() == null || usuarioCrearDto.getUsername().trim().isEmpty()) {
		        throw new RuntimeException("El username es obligatorio");
		    }

		    if (usuarioCrearDto.getNombre() == null || usuarioCrearDto.getNombre().trim().isEmpty()) {
		        throw new RuntimeException("El nombre es obligatorio");
		    }

		    if (usuarioCrearDto.getEmail() == null || usuarioCrearDto.getEmail().trim().isEmpty()) {
		        throw new RuntimeException("El email es obligatorio");
		    }

		    if (usuarioCrearDto.getRol() == null) {
		        throw new RuntimeException("El rol es obligatorio");
		    }

		    Usuario usuario = usuarioRepositorio.findById(id)
		            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

		    usuario.setUsername(usuarioCrearDto.getUsername().trim());
		    usuario.setNombre(usuarioCrearDto.getNombre().trim());
		    usuario.setEmail(usuarioCrearDto.getEmail().trim());
		    usuario.setRol(usuarioCrearDto.getRol());
		    usuario.setActivo(usuarioCrearDto.isActivo());

		    if (usuarioCrearDto.getPassword() != null 
		            && !usuarioCrearDto.getPassword().trim().isEmpty()) {

		        usuario.setPassword(usuarioCrearDto.getPassword().trim());
		    }

		    Usuario usuarioActualizado = usuarioRepositorio.save(usuario);

		    return new UsuarioCrearDto(
		            usuarioActualizado.getId(),
		            usuarioActualizado.getUsername(),
		            usuarioActualizado.getNombre(),
		            usuarioActualizado.getEmail(),
		            usuarioActualizado.getPassword(),
		            usuarioActualizado.getRol(),
		            usuarioActualizado.isActivo()
		    );
	}

	@Override
	public List<UsuarioCrearDto> listarUsuariosC() {
		List<Usuario> UsuariosModelo = usuarioRepositorio.findAll();
		return UsuariosModelo.stream().map(usuario -> modelMapper.map(usuario, UsuarioCrearDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public UsuarioCrearDto buscarUsuarioPorIdC(Long id) {
		Usuario usuario = usuarioRepositorio.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
		return modelMapper.map(usuario, UsuarioCrearDto.class);
	}

}

package com.oscarcoronado.proyectofinal.gestiondesperfectos.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oscarcoronado.proyectofinal.gestiondesperfectos.dto.UsuarioDto;
import com.oscarcoronado.proyectofinal.gestiondesperfectos.servicioImp.UsuarioServicioImp;

@Controller
@RequestMapping("/api/usuario")
public class UsuarioControlador {
	
	@Autowired
	private UsuarioServicioImp usuarioServicio;
	
	// Lista a todos los jugadores
		@GetMapping("/listar")
		public ResponseEntity<List<UsuarioDto>> listarUsuarios() {
			
			List<UsuarioDto> usuario = usuarioServicio.listarUsuarios();
			
			return new ResponseEntity<>(usuario, HttpStatus.OK);
			
		}
		
		@GetMapping("/{id}")
		public ResponseEntity<UsuarioDto> buscarUsuarioPorId(@PathVariable Long id){
			
			UsuarioDto usuario = usuarioServicio.buscarUsuarioPorId(id);
			return new ResponseEntity<>(usuario, HttpStatus.OK);
			
		}
		
		@DeleteMapping("/borrar/{id}")
		public ResponseEntity<String> eliminarUsuarioPorId(@PathVariable Long id){
			usuarioServicio.eliminarUsuarioPorId(id);
			return new ResponseEntity<>("Se ha borrado correctamente", HttpStatus.OK);
		}
		
		@DeleteMapping("/borrar/todos")
		public ResponseEntity<String> eliminarTodasLosUsuarios(){
			usuarioServicio.eliminarTodosLosUsuarios();
			return new ResponseEntity<>("Todas las usuarios se han borrado correctamente", HttpStatus.OK);
		}

}

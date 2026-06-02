package com.oscarcoronado.proyectofinal.gestiondesperfectos.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oscarcoronado.proyectofinal.gestiondesperfectos.dto.AulaDto;
import com.oscarcoronado.proyectofinal.gestiondesperfectos.servicioImp.AulaServicioImp;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/aula")
public class AulaControlador {

	@Autowired
	private AulaServicioImp aulaServicio;
	
	// Lista a todos los jugadores
		@GetMapping("/listar")
		public ResponseEntity<List<AulaDto>> listarAulas() {
			
			List<AulaDto> aula = aulaServicio.listarAulas();
			
			return new ResponseEntity<>(aula, HttpStatus.OK);
			
		}
		
		@GetMapping("/{id}")
		public ResponseEntity<AulaDto> buscarAulaPorId(@PathVariable Long id){
			
			AulaDto aula = aulaServicio.buscarAulaPorId(id);
			return new ResponseEntity<>(aula, HttpStatus.OK);
			
		}

		@PostMapping("/crear")
	    public ResponseEntity<AulaDto> crearAula(@Valid @RequestBody AulaDto dto) {
			AulaDto creado = aulaServicio.crearAula(dto);
			return ResponseEntity.status(HttpStatus.CREATED).body(creado);
	    }
		
		@PutMapping("/{id}")
		public AulaDto editarAula(
		        @PathVariable Long id,
		        @RequestBody AulaDto aulaDto) {

		    return aulaServicio.editar(id, aulaDto);
		}
		
		@DeleteMapping("/borrar/{id}")
		public ResponseEntity<String> eliminarAulaPorId(@PathVariable Long id){
			aulaServicio.eliminarAulaPorid(id);
			return new ResponseEntity<>("Se ha borrado correctamente", HttpStatus.OK);
		}
		
		@DeleteMapping("/borrar/todos")
		public ResponseEntity<String> eliminarTodasLasAulas(){
			aulaServicio.eliminarTodosLasAulas();
			return new ResponseEntity<>("Todas las aulas se han borrado correctamente", HttpStatus.OK);
		}
}

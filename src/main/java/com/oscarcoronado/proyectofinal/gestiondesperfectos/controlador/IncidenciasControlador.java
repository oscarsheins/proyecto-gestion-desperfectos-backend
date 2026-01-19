package com.oscarcoronado.proyectofinal.gestiondesperfectos.controlador;



import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oscarcoronado.proyectofinal.gestiondesperfectos.dto.IncidenciaCreaDto;
import com.oscarcoronado.proyectofinal.gestiondesperfectos.dto.IncidenciaDto;
import com.oscarcoronado.proyectofinal.gestiondesperfectos.dto.IncidenciaEstadoDto;
import com.oscarcoronado.proyectofinal.gestiondesperfectos.servicio.IncidenciaServicio;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/incidencias")
@AllArgsConstructor
public class IncidenciasControlador {

    private final IncidenciaServicio incidenciaService;

    // Alta incidencia
    @PostMapping
    public ResponseEntity<IncidenciaDto> crear(@RequestBody IncidenciaCreaDto dto) {
        IncidenciaDto creada = incidenciaService.crear(dto);
        return ResponseEntity
                .created(URI.create("/api/incidencias/" + creada.getId()))
                .body(creada); // 201
    }

    // Listado ALL o filtrado por usuario: /api/incidencias?usuarioId=1
    @GetMapping
    public ResponseEntity<List<IncidenciaDto>> listAll(@RequestParam(required = false) Long usuarioId) {
        List<IncidenciaDto> res = (usuarioId == null)
                ? incidenciaService.listAll()
                : incidenciaService.listByUsuario(usuarioId);
        return ResponseEntity.ok(res); // 200
    }

    // Listado por ID
    @GetMapping("/{id}")
    public ResponseEntity<IncidenciaDto> listById(@PathVariable Long id) {
        return ResponseEntity.ok(incidenciaService.listById(id)); // 200
    }

    // Cambio de estado
    @PutMapping("/{id}/estado")
    public ResponseEntity<IncidenciaDto> cambiarEstado(@PathVariable Long id,
                                                       @RequestBody IncidenciaEstadoDto dto) {
        return ResponseEntity.ok(incidenciaService.cambiarEstado(id, dto.getEstadoId())); // 200
    }

    // Borrado por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarById(@PathVariable Long id) {
        incidenciaService.borrarById(id);
        return ResponseEntity.noContent().build(); // 204
    }

    // Borrado ALL
    @DeleteMapping
    public ResponseEntity<Void> borrarAll() {
        incidenciaService.borrarAll();
        return ResponseEntity.noContent().build(); // 204
    }
}


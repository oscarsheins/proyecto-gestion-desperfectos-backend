package com.oscarcoronado.proyectofinal.gestiondesperfectos.servicioImp;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oscarcoronado.proyectofinal.gestiondesperfectos.dto.IncidenciaCreaDto;
import com.oscarcoronado.proyectofinal.gestiondesperfectos.dto.IncidenciaDto;
import com.oscarcoronado.proyectofinal.gestiondesperfectos.entidad.Aula;
import com.oscarcoronado.proyectofinal.gestiondesperfectos.entidad.Estado;
import com.oscarcoronado.proyectofinal.gestiondesperfectos.entidad.Incidencia;
import com.oscarcoronado.proyectofinal.gestiondesperfectos.entidad.Usuario;
import com.oscarcoronado.proyectofinal.gestiondesperfectos.repositorio.AulaRepositorio;
import com.oscarcoronado.proyectofinal.gestiondesperfectos.repositorio.EstadoRepositorio;
import com.oscarcoronado.proyectofinal.gestiondesperfectos.repositorio.IncidenciasRepositorio;
import com.oscarcoronado.proyectofinal.gestiondesperfectos.repositorio.UsuarioRepositorio;
import com.oscarcoronado.proyectofinal.gestiondesperfectos.servicio.IncidenciaServicio;

import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class IncidenciaServicioImp implements IncidenciaServicio {

	
    private final IncidenciasRepositorio incidenciaRepository;
    private final UsuarioRepositorio usuarioRepository;
    private final EstadoRepositorio estadoRepository;
    private final AulaRepositorio aulaRepository;


    @Override
    public IncidenciaDto crear(IncidenciaCreaDto dto) {
        validarAlta(dto);

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Estado estado = estadoRepository.findById(dto.getEstadoId())
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        Aula aula = aulaRepository.findById(dto.getAulaId())
                .orElseThrow(() -> new RuntimeException("Aula no encontrada"));

        Incidencia incidencia = new Incidencia(
                dto.getTitulo().trim(),
                dto.getDescripcion().trim(),
                usuario,
                estado,
                aula
        );

        Incidencia guardada = incidenciaRepository.save(incidencia);
        return toDto(guardada);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IncidenciaDto> listAll() {
        return incidenciaRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public IncidenciaDto listById(Long id) {
        Incidencia incidencia = incidenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Incidencia no encontrada"));
        return toDto(incidencia);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IncidenciaDto> listByUsuario(Long usuarioId) {
        if (usuarioId == null) {
            throw new RuntimeException("usuarioId es obligatorio");
        }

        return incidenciaRepository.findByUsuario_Id(usuarioId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public IncidenciaDto cambiarEstado(Long incidenciaId, Long nuevoEstadoId) {
        if (incidenciaId == null || nuevoEstadoId == null) {
            throw new RuntimeException("incidenciaId y estadoId son obligatorios");
        }

        Incidencia incidencia = incidenciaRepository.findById(incidenciaId)
                .orElseThrow(() -> new RuntimeException("Incidencia no encontrada"));

        Estado estado = estadoRepository.findById(nuevoEstadoId)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        incidencia.setEstado(estado);
        Incidencia actualizada = incidenciaRepository.save(incidencia);

        return toDto(actualizada);
    }

    @Override
    public void borrarById(Long id) {
        if (id == null) throw new RuntimeException("id es obligatorio");

        if (!incidenciaRepository.existsById(id)) {
            throw new RuntimeException("Incidencia no encontrada");
        }
        incidenciaRepository.deleteById(id);
    }

    @Override
    public void borrarAll() {
        incidenciaRepository.deleteAll();
    }

    private void validarAlta(IncidenciaCreaDto dto) {
        if (dto == null) throw new RuntimeException("Body requerido");
        if (dto.getTitulo() == null || dto.getTitulo().trim().isEmpty())
            throw new RuntimeException("titulo es obligatorio");
        if (dto.getDescripcion() == null || dto.getDescripcion().trim().isEmpty())
            throw new RuntimeException("descripcion es obligatoria");
        if (dto.getUsuarioId() == null)
            throw new RuntimeException("usuarioId es obligatorio");
        if (dto.getEstadoId() == null)
            throw new RuntimeException("estadoId es obligatorio");
        if (dto.getAulaId() == null)
            throw new RuntimeException("aulaId es obligatorio");
    }

    private IncidenciaDto toDto(Incidencia i) {
        Long aulaId = (i.getAula() != null) ? i.getAula().getId() : null;
        String aulaCodigo = (i.getAula() != null) ? i.getAula().getCodigo() : null;

        return new IncidenciaDto(
                i.getId(),
                i.getTitulo(),
                i.getDescripcion(),
                i.getFechaCreacion(),
                i.getFechaActualizacion(),
                i.getUsuario().getId(),
                i.getUsuario().getUsername(),
                i.getEstado().getId(),
                i.getEstado().getNombre(),
                aulaId,
                aulaCodigo
        );
    }
	
}

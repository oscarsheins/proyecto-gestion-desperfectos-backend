package com.oscarcoronado.proyectofinal.gestiondesperfectos.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oscarcoronado.proyectofinal.gestiondesperfectos.entidad.Incidencia;

@Repository
public interface IncidenciasRepositorio extends JpaRepository<Incidencia, Long> {

	List<Incidencia> findByUsuario_Id(Long usuarioId);
	
}

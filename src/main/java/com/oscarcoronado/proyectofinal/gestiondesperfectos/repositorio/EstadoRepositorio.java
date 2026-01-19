package com.oscarcoronado.proyectofinal.gestiondesperfectos.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oscarcoronado.proyectofinal.gestiondesperfectos.entidad.Estado;

@Repository
public interface EstadoRepositorio extends JpaRepository<Estado, Long> {

	Optional<Estado> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
	
}

package com.oscarcoronado.proyectofinal.gestiondesperfectos.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oscarcoronado.proyectofinal.gestiondesperfectos.entidad.Aula;

@Repository
public interface AulaRepositorio extends JpaRepository<Aula, Long >{
	
	Optional<Aula> findByCodigo(String codigo);
    boolean existsByCodigo(String codigo);

}

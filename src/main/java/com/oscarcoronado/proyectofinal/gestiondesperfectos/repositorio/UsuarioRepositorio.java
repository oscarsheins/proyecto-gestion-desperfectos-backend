package com.oscarcoronado.proyectofinal.gestiondesperfectos.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oscarcoronado.proyectofinal.gestiondesperfectos.entidad.Usuario;


@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
	
    Optional<Usuario> findByUsername(String username);
    boolean existsByUsername(String username);


}

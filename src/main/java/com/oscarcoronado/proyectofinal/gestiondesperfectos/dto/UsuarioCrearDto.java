package com.oscarcoronado.proyectofinal.gestiondesperfectos.dto;

import com.oscarcoronado.proyectofinal.gestiondesperfectos.entidad.RolUsuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioCrearDto {
	
	private Long id;
    private String username;
    private String nombre;
    private String email;
    private String password;
    private RolUsuario rol;
    private boolean activo = true;

}

package com.oscarcoronado.proyectofinal.gestiondesperfectos.entidad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "usuario")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "usuarios",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_usuarios_username", columnNames = "username"),
                @UniqueConstraint(name = "uk_usuarios_email", columnNames = "email")
        }
)
public class Usuario {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false,length = 120)
    private String email;

//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false, length = 30)
//    private RolUsuario rol;

    @Column(nullable = false)
    private boolean activo = true;

}

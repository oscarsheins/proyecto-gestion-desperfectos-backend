package com.oscarcoronado.proyectofinal.gestiondesperfectos.entidad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "aula")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Aula {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String codigo; // A-101, LAB-2, etc.
    
    @Column
    private String descripcion;
}

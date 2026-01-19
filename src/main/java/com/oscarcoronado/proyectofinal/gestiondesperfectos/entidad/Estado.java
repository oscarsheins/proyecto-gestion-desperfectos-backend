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

@Entity(name = "estado")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "estados",
        uniqueConstraints = @UniqueConstraint(name = "uk_estados_nombre", columnNames = "nombre")
)
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String nombre; // ABIERTA, EN_PROCESO, RESUELTA...

    @Column(nullable = false)
    private int orden = 0;
	
}

package com.oscarcoronado.proyectofinal.gestiondesperfectos.entidad;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "incidencia")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "incidencias")
public class Incidencia {
	
	@PrePersist
	public void prePersist() {
	    this.fechaCreacion = LocalDateTime.now();
	    this.fechaActualizacion = this.fechaCreacion;
	}

	@PreUpdate
	public void preUpdate() {
	    this.fechaActualizacion = LocalDateTime.now();
	}
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String titulo;

    @Lob
    @Column(nullable = false)
    private String descripcion;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion", nullable = false)
    private LocalDateTime fechaActualizacion;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Prioridad prioridad;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_incidencias_usuario"))
    private Usuario usuario;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "estado_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_incidencias_estado"))
    private Estado estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aula_id",
            foreignKey = @ForeignKey(name = "fk_incidencias_aula"))
    private Aula aula;
    
    public Incidencia(String titulo, String descripcion, Usuario usuario, Estado estado, Aula aula) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.usuario = usuario;
        this.estado = estado;
        this.aula = aula;
    }

}

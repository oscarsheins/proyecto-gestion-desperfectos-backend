package com.oscarcoronado.proyectofinal.gestiondesperfectos;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EntityScan(basePackages = "com.oscarcoronado.proyectofinal.gestiondesperfectos.entidad" )
public class GestionDesperfectosApplication {
	
	@Bean
	public ModelMapper convertidor() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(GestionDesperfectosApplication.class, args);
	}

}

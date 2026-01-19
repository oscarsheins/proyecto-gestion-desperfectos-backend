package com.oscarcoronado.proyectofinal.gestiondesperfectos.servicioImp;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oscarcoronado.proyectofinal.gestiondesperfectos.dto.AulaDto;
import com.oscarcoronado.proyectofinal.gestiondesperfectos.entidad.Aula;
import com.oscarcoronado.proyectofinal.gestiondesperfectos.repositorio.AulaRepositorio;
import com.oscarcoronado.proyectofinal.gestiondesperfectos.servicio.AulaServicio;

@Service
public class AulaServicioImp implements AulaServicio{
	
	@Autowired
	private AulaRepositorio aulaRepositorio;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<AulaDto> listarAulas() {
		List<Aula> aulasModelo = aulaRepositorio.findAll();
		return aulasModelo.stream().map(aula -> modelMapper.map(aula, AulaDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public AulaDto buscarAulaPorId(Long id) {
		Aula aula = aulaRepositorio.findById(id).orElseThrow(() -> new IllegalArgumentException("Aula no encontrada"));
		return modelMapper.map(aula, AulaDto.class);
	}

	@Override
	public void eliminarTodosLasAulas() {
		aulaRepositorio.deleteAll();
		
	}

	@Override
	public void eliminarAulaPorid(Long id) {
		
	    if (!aulaRepositorio.existsById(id)) {
	        throw new IllegalArgumentException("El aula con id " + id + " no existe");
	    }
		
		aulaRepositorio.deleteById(id);
		
	}
	
	@Override
	public AulaDto crearAula(AulaDto aulaDto) {

		Aula aula = modelMapper.map(aulaDto, Aula.class);
		Aula aulaGurdada = aulaRepositorio.save(aula);
		return modelMapper.map(aulaGurdada, AulaDto.class);
		
	}
	

}

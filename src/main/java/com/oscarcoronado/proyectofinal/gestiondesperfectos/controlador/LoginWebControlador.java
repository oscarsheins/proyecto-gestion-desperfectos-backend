package com.oscarcoronado.proyectofinal.gestiondesperfectos.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oscarcoronado.proyectofinal.gestiondesperfectos.dto.IncidenciaCreaDto;
import com.oscarcoronado.proyectofinal.gestiondesperfectos.entidad.Usuario;
import com.oscarcoronado.proyectofinal.gestiondesperfectos.servicio.IncidenciaServicio;
import com.oscarcoronado.proyectofinal.gestiondesperfectos.servicio.UsuarioServicio;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginWebControlador {

	@Autowired
	private UsuarioServicio usuarioServicio;
	
	@Autowired
    private IncidenciaServicio incidenciaServicio;

	@GetMapping("/login")
	public String mostrarLogin() {
		return "login";
	}

	@PostMapping("/login")
	public String login(@RequestParam String usuario, @RequestParam String password, Model model, HttpSession session) {

		Usuario usuarioLogueado = usuarioServicio.login(usuario, password);

		if (usuarioLogueado == null) {
			model.addAttribute("error", "Usuario o contraseña incorrectos");
			return "login";
		}

		session.setAttribute("usuarioId", usuarioLogueado.getId());
		session.setAttribute("nombreUsuario", usuarioLogueado.getNombre());

		return "redirect:/home";
	}

	@GetMapping("/home")
	public String mostrarHome(Model model, HttpSession session) {

		model.addAttribute("nombreUsuario", session.getAttribute("nombreUsuario"));
		model.addAttribute("incidencia", new IncidenciaCreaDto());

		return "home";
	}	
	
	@PostMapping("/home/incidencia")
	public String crearIncidencia(
	        @ModelAttribute IncidenciaCreaDto incidencia,
	        HttpSession session,
	        Model model) {

	    Long usuarioId = (Long) session.getAttribute("usuarioId");

	    incidencia.setUsuarioId(usuarioId);
	    incidencia.setEstadoId(1L); // Pendiente de momento
	    incidencia.setAulaId(1L);   // De momento fija

	    incidenciaServicio.crear(incidencia);

	    model.addAttribute("nombreUsuario", session.getAttribute("nombreUsuario"));
	    model.addAttribute("mensaje", "Incidencia enviada correctamente");
	    model.addAttribute("incidencia", new IncidenciaCreaDto());

	    return "home";
	}
}

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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class LoginWebControlador {
	
	private static final Logger logger =
            LogManager.getLogger(LoginWebControlador.class);

	@Autowired
	private UsuarioServicio usuarioServicio;
	
	@Autowired
    private IncidenciaServicio incidenciaServicio;

	@GetMapping("/login")
	public String mostrarLogin() {
		return "login";
	}

	@PostMapping("/login")
	public String login(
	        @RequestParam String usuario,
	        @RequestParam String password,
	        Model model,
	        HttpSession session) {

	    Usuario usuarioLogueado = usuarioServicio.login(usuario, password);

	    if (usuarioLogueado == null) {
	        model.addAttribute("error", "Usuario o contraseña incorrectos");
	        return "login";
	    }

	    session.setAttribute("usuarioId", usuarioLogueado.getId());
	    session.setAttribute("nombreUsuario", usuarioLogueado.getNombre());
	    session.setAttribute("rolUsuario", usuarioLogueado.getRol());

	    logger.info("Rol: {}", usuarioLogueado.getRol());

	    if (usuarioLogueado.getRol().name().equals("MANTENIMIENTO")) {
	        return "redirect:/mantenimiento/home";
	    }

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

	    if (usuarioId == null) {
	        return "redirect:/login";
	    }

	    try {
	        incidencia.setUsuarioId(usuarioId);
	        incidencia.setEstadoId(1L); // Pendiente de momento

	        incidenciaServicio.crear(incidencia);

	        model.addAttribute("mensaje", "Incidencia enviada correctamente");
	        model.addAttribute("incidencia", new IncidenciaCreaDto());

	    } catch (RuntimeException e) {

	        model.addAttribute("error", e.getMessage());
	        model.addAttribute("incidencia", incidencia);
	    }

	    model.addAttribute("nombreUsuario", session.getAttribute("nombreUsuario"));

	    return "home";
	}
	
	@GetMapping("/mis-incidencias")
	public String verMisIncidencias(HttpSession session, Model model) {

	    Long usuarioId = (Long) session.getAttribute("usuarioId");

	    if (usuarioId == null) {
	        return "redirect:/login";
	    }

	    model.addAttribute("nombreUsuario", session.getAttribute("nombreUsuario"));
	    model.addAttribute("incidencias", incidenciaServicio.listByUsuario(usuarioId));

	    return "mis-incidencias";
	}
		
	
	@GetMapping("/mantenimiento/home")
	public String mostrarHomeMantenimiento(HttpSession session, Model model) {

	    if (session.getAttribute("usuarioId") == null) {
	        return "redirect:/login";
	    }

	    if (!session.getAttribute("rolUsuario").toString().equals("MANTENIMIENTO")) {
	        return "redirect:/home";
	    }

	    model.addAttribute("nombreUsuario", session.getAttribute("nombreUsuario"));
	    model.addAttribute("incidencias", incidenciaServicio.listActivas());

	    return "mantenimiento-home";
	}
	
	@GetMapping("/mantenimiento/incidencia/{id}")
	public String verDetalleIncidenciaMantenimiento(
	        @PathVariable Long id,
	        HttpSession session,
	        Model model) {

	    if (session.getAttribute("usuarioId") == null) {
	        return "redirect:/login";
	    }

	    if (!session.getAttribute("rolUsuario").toString().equals("MANTENIMIENTO")) {
	        return "redirect:/home";
	    }

	    model.addAttribute("nombreUsuario", session.getAttribute("nombreUsuario"));
	    model.addAttribute("incidencia", incidenciaServicio.listById(id));

	    return "mantenimiento-detalle";
	}

	@PostMapping("/mantenimiento/incidencia/{id}/en-curso")
	public String ponerIncidenciaEnCurso(
	        @PathVariable Long id,
	        HttpSession session) {

	    if (session.getAttribute("usuarioId") == null) {
	        return "redirect:/login";
	    }

	    if (!session.getAttribute("rolUsuario").toString().equals("MANTENIMIENTO")) {
	        return "redirect:/home";
	    }

	    incidenciaServicio.cambiarEstado(id, 2L);

	    return "redirect:/mantenimiento/incidencia/" + id;
	}

	@PostMapping("/mantenimiento/incidencia/{id}/finalizar")
	public String finalizarIncidencia(
	        @PathVariable Long id,
	        HttpSession session) {

	    if (session.getAttribute("usuarioId") == null) {
	        return "redirect:/login";
	    }

	    if (!session.getAttribute("rolUsuario").toString().equals("MANTENIMIENTO")) {
	        return "redirect:/home";
	    }

	    incidenciaServicio.cambiarEstado(id, 3L);

	    return "redirect:/mantenimiento/home";
	}
	
	
}

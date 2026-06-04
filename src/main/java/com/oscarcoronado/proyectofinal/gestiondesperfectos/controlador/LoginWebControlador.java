	package com.oscarcoronado.proyectofinal.gestiondesperfectos.controlador;
	
	import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oscarcoronado.proyectofinal.gestiondesperfectos.dto.AulaDto;
import com.oscarcoronado.proyectofinal.gestiondesperfectos.dto.IncidenciaCreaDto;
import com.oscarcoronado.proyectofinal.gestiondesperfectos.dto.IncidenciaEditaDto;
import com.oscarcoronado.proyectofinal.gestiondesperfectos.dto.UsuarioCrearDto;
import com.oscarcoronado.proyectofinal.gestiondesperfectos.entidad.Usuario;
import com.oscarcoronado.proyectofinal.gestiondesperfectos.servicio.AulaServicio;
import com.oscarcoronado.proyectofinal.gestiondesperfectos.servicio.IncidenciaServicio;
import com.oscarcoronado.proyectofinal.gestiondesperfectos.servicio.UsuarioServicio;

import jakarta.servlet.http.HttpSession;
	
	@Controller
	public class LoginWebControlador {
		
		private static final Logger logger =
	            LogManager.getLogger(LoginWebControlador.class);
		
		@Autowired
		private UsuarioServicio usuarioServicio;
		
		@Autowired
	    private IncidenciaServicio incidenciaServicio;
		
		@Autowired
		private AulaServicio aulaServicio;
		
	
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
		    
		    if (usuarioLogueado.getRol().name().equals("ADMIN")) {
		        return "redirect:/admin/home";
		    }
	
		    if (usuarioLogueado.getRol().name().equals("MANTENIMIENTO")) {
		        return "redirect:/mantenimiento/home";
		    }
	
		    return "redirect:/home";
		}
	
		@GetMapping("/home")
		public String mostrarHome(Model model, HttpSession session) {

		    if (session.getAttribute("usuarioId") == null) {
		        return "redirect:/login";
		    }

		    model.addAttribute("nombreUsuario", session.getAttribute("nombreUsuario"));
		    model.addAttribute("incidencia", new IncidenciaCreaDto());
		    model.addAttribute("aulas", aulaServicio.listarAulas());

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
		    model.addAttribute("aulas", aulaServicio.listarAulas());
	
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
		
		@GetMapping("/mis-incidencias/{id}")
		public String verDetalleMiIncidencia(
		        @PathVariable Long id,
		        HttpSession session,
		        Model model) {
	
		    Long usuarioId = (Long) session.getAttribute("usuarioId");
	
		    if (usuarioId == null) {
		        return "redirect:/login";
		    }
	
		    var incidencia = incidenciaServicio.listById(id);
	
		    if (!incidencia.getUsuarioId().equals(usuarioId)) {
		        return "redirect:/mis-incidencias";
		    }
	
		    model.addAttribute("nombreUsuario", session.getAttribute("nombreUsuario"));
		    model.addAttribute("incidencia", incidencia);
	
		    return "mis-incidencia-detalle";
		}
	
		@GetMapping("/mis-incidencias/{id}/editar")
		public String mostrarEditarMiIncidencia(
		        @PathVariable Long id,
		        HttpSession session,
		        Model model) {
	
		    Long usuarioId = (Long) session.getAttribute("usuarioId");
	
		    if (usuarioId == null) {
		        return "redirect:/login";
		    }
	
		    var incidencia = incidenciaServicio.listById(id);
	
		    if (!incidencia.getUsuarioId().equals(usuarioId)) {
		        return "redirect:/mis-incidencias";
		    }
	
		    IncidenciaEditaDto dto = new IncidenciaEditaDto(
		            incidencia.getId(),
		            incidencia.getTitulo(),
		            incidencia.getDescripcion(),
		            incidencia.getAulaId()
		    );
	
		    model.addAttribute("nombreUsuario", session.getAttribute("nombreUsuario"));
		    model.addAttribute("incidencia", dto);
		    model.addAttribute("aulas", aulaServicio.listarAulas());
	
		    return "mis-incidencia-editar";
		}
		
		@PostMapping("/mis-incidencias/{id}/editar")
		public String guardarEditarMiIncidencia(
		        @PathVariable Long id,
		        @ModelAttribute IncidenciaEditaDto incidencia,
		        HttpSession session,
		        Model model) {
	
		    Long usuarioId = (Long) session.getAttribute("usuarioId");
	
		    if (usuarioId == null) {
		        return "redirect:/login";
		    }
	
		    var incidenciaBD = incidenciaServicio.listById(id);
	
		    if (!incidenciaBD.getUsuarioId().equals(usuarioId)) {
		        return "redirect:/mis-incidencias";
		    }
	
		    try {
		        incidenciaServicio.editar(id, incidencia);
		        return "redirect:/mis-incidencias/" + id;
	
		    } catch (RuntimeException e) {
	
		        incidencia.setId(id);
	
		        model.addAttribute("nombreUsuario", session.getAttribute("nombreUsuario"));
		        model.addAttribute("incidencia", incidencia);
		        model.addAttribute("error", e.getMessage());
		        model.addAttribute("aulas", aulaServicio.listarAulas());
	
		        return "mis-incidencia-editar";
		    }
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
		
		// Vistas de administrador 
		
		//Vista del home
		@GetMapping("/admin/home")
		public String mostrarHomeAdmin(HttpSession session, Model model) {
	
		    if (session.getAttribute("usuarioId") == null) {
		        return "redirect:/login";
		    }
	
		    if (!session.getAttribute("rolUsuario").toString().equals("ADMIN")) {
		        return "redirect:/home";
		    }
	
		    model.addAttribute("nombreUsuario", session.getAttribute("nombreUsuario"));
	
		    return "admin-home";
		}
		
		// Vistas para crear aulas nuevas
		@GetMapping("/admin/aulas/nueva")
		public String mostrarCrearAula(HttpSession session, Model model) {
	
		    if (session.getAttribute("usuarioId") == null) {
		        return "redirect:/login";
		    }
	
		    if (!session.getAttribute("rolUsuario").toString().equals("ADMIN")) {
		        return "redirect:/home";
		    }
	
		    model.addAttribute("nombreUsuario", session.getAttribute("nombreUsuario"));
		    model.addAttribute("aula", new AulaDto());
	
		    return "admin-aula";
		}
		
		@PostMapping("/admin/aulas/nueva")
		public String crearAula(
		        @ModelAttribute AulaDto aula,
		        HttpSession session,
		        Model model) {
	
		    if (session.getAttribute("usuarioId") == null) {
		        return "redirect:/login";
		    }
	
		    if (!session.getAttribute("rolUsuario").toString().equals("ADMIN")) {
		        return "redirect:/home";
		    }
	
		    try {
		        aulaServicio.crearAula(aula);
	
		        model.addAttribute("mensaje", "Aula creada correctamente");
		        model.addAttribute("aula", new AulaDto());
	
		    } catch (RuntimeException e) {
		        model.addAttribute("error", e.getMessage());
		        model.addAttribute("aula", aula);
		    }
	
		    model.addAttribute("nombreUsuario", session.getAttribute("nombreUsuario"));
	
		    return "admin-aula";
		}
		
		//vista para editar las aulas
		@GetMapping("/admin/aulas/{id}/editar")
		public String mostrarEditarAula(
		        @PathVariable Long id,
		        HttpSession session,
		        Model model) {

		    if (session.getAttribute("usuarioId") == null) {
		        return "redirect:/login";
		    }

		    if (!session.getAttribute("rolUsuario").toString().equals("ADMIN")) {
		        return "redirect:/home";
		    }

		    model.addAttribute("nombreUsuario",
		            session.getAttribute("nombreUsuario"));

		    model.addAttribute("aula",
		            aulaServicio.buscarAulaPorId(id));

		    model.addAttribute("aulaId", id);

		    return "admin-aula-editar";
		}
		
		@PostMapping("/admin/aulas/{id}/editar")
		public String editarAula(
		        @PathVariable Long id,
		        @ModelAttribute AulaDto aula,
		        HttpSession session,
		        Model model) {

		    if (session.getAttribute("usuarioId") == null) {
		        return "redirect:/login";
		    }

		    if (!session.getAttribute("rolUsuario").toString().equals("ADMIN")) {
		        return "redirect:/home";
		    }

		    try {

		        aulaServicio.editar(id, aula);

		        return "redirect:/admin/aulas/" + id;

		    } catch (RuntimeException e) {

		        model.addAttribute("nombreUsuario",
		                session.getAttribute("nombreUsuario"));

		        model.addAttribute("aula", aula);
		        model.addAttribute("aulaId", id);
		        model.addAttribute("error", e.getMessage());

		        return "admin-aula-editar";
		    }
		}
		
		//Vista borrar aulas 
		@PostMapping("/admin/aulas/{id}/eliminar")
		public String eliminarAulaAdmin(
		        @PathVariable Long id,
		        HttpSession session) {

		    if (session.getAttribute("usuarioId") == null) {
		        return "redirect:/login";
		    }

		    if (!session.getAttribute("rolUsuario").toString().equals("ADMIN")) {
		        return "redirect:/home";
		    }

		    aulaServicio.eliminarAulaPorid(id);

		    return "redirect:/admin/aulas";
		}
		
		// Vista para ver las aulas
		@GetMapping("/admin/aulas")
		public String listarAulasAdmin(HttpSession session, Model model) {

		    if (session.getAttribute("usuarioId") == null) {
		        return "redirect:/login";
		    }

		    if (!session.getAttribute("rolUsuario").toString().equals("ADMIN")) {
		        return "redirect:/home";
		    }

		    model.addAttribute("nombreUsuario", session.getAttribute("nombreUsuario"));
		    model.addAttribute("aulas", aulaServicio.listarAulas());

		    return "admin-aulas";
		}
		
		//Vista para ver las aulas por separado
		@GetMapping("/admin/aulas/{id}")
		public String verDetalleAula(
		        @PathVariable Long id,
		        HttpSession session,
		        Model model) {

		    model.addAttribute("nombreUsuario",
		            session.getAttribute("nombreUsuario"));

		    model.addAttribute("aula",
		            aulaServicio.buscarAulaPorId(id));

		    return "admin-aula-detalle";
		}
		
		//vista para crear usurios nuevos 
		@GetMapping("/admin/usuarios/nuevo")
		public String mostrarCrearUsuario(HttpSession session, Model model) {
	
		    if (session.getAttribute("usuarioId") == null) {
		        return "redirect:/login";
		    }
	
		    if (!session.getAttribute("rolUsuario").toString().equals("ADMIN")) {
		        return "redirect:/home";
		    }
	
		    model.addAttribute("nombreUsuario", session.getAttribute("nombreUsuario"));
		    model.addAttribute("usuarioNuevo", new UsuarioCrearDto());
	
		    return "admin-usuario";
		}
	
		@PostMapping("/admin/usuarios/nuevo")
		public String crearUsuario(
		        @ModelAttribute UsuarioCrearDto usuarioNuevo,
		        HttpSession session,
		        Model model) {
	
		    if (session.getAttribute("usuarioId") == null) {
		        return "redirect:/login";
		    }
	
		    if (!session.getAttribute("rolUsuario").toString().equals("ADMIN")) {
		        return "redirect:/home";
		    }
	
		    try {
		        usuarioServicio.crearUsuario(usuarioNuevo);
	
		        model.addAttribute("mensaje", "Usuario creado correctamente");
		        model.addAttribute("usuarioNuevo", new UsuarioCrearDto());
	
		    } catch (RuntimeException e) {
		        model.addAttribute("error", e.getMessage());
		        model.addAttribute("usuarioNuevo", usuarioNuevo);
		    }
	
		    model.addAttribute("nombreUsuario", session.getAttribute("nombreUsuario"));
	
		    return "admin-usuario";
		}
		
		//Vista para ver todos los usuarios 
		@GetMapping("/admin/usuarios")
		public String listarUsuariosAdmin(HttpSession session, Model model) {

		    if (session.getAttribute("usuarioId") == null) {
		        return "redirect:/login";
		    }

		    if (!session.getAttribute("rolUsuario").toString().equals("ADMIN")) {
		        return "redirect:/home";
		    }

		    model.addAttribute("nombreUsuario", session.getAttribute("nombreUsuario"));
		    model.addAttribute("usuarios", usuarioServicio.listarUsuariosC());

		    return "admin-usuarios";
		}
		
		@GetMapping("/admin/usuarios/{id}")
		public String verDetalleUsuario(
		        @PathVariable Long id,
		        HttpSession session,
		        Model model) {

		    if (session.getAttribute("usuarioId") == null) {
		        return "redirect:/login";
		    }

		    if (!session.getAttribute("rolUsuario").toString().equals("ADMIN")) {
		        return "redirect:/home";
		    }

		    model.addAttribute("nombreUsuario", session.getAttribute("nombreUsuario"));
		    model.addAttribute("usuario", usuarioServicio.buscarUsuarioPorIdC(id));

		    return "admin-usuario-detalle";
		}
		
		@PostMapping("/admin/usuarios/{id}/editar")
		public String editarUsuario(
		        @PathVariable Long id,
		        @ModelAttribute UsuarioCrearDto usuarioNuevo,
		        HttpSession session,
		        Model model) {

		    if (session.getAttribute("usuarioId") == null) {
		        return "redirect:/login";
		    }

		    if (!session.getAttribute("rolUsuario").toString().equals("ADMIN")) {
		        return "redirect:/home";
		    }

		    try {
		        usuarioServicio.editar(id, usuarioNuevo);
		        return "redirect:/admin/usuarios/" + id;

		    } catch (RuntimeException e) {
		        model.addAttribute("nombreUsuario", session.getAttribute("nombreUsuario"));
		        model.addAttribute("usuarioNuevo", usuarioNuevo);
		        model.addAttribute("usuarioId", id);
		        model.addAttribute("error", e.getMessage());

		        return "admin-usuario-editar";
		    }
		}
		
		@PostMapping("/admin/usuarios/{id}/eliminar")
		public String eliminarUsuarioAdmin(
		        @PathVariable Long id,
		        HttpSession session) {

		    if (session.getAttribute("usuarioId") == null) {
		        return "redirect:/login";
		    }

		    if (!session.getAttribute("rolUsuario").toString().equals("ADMIN")) {
		        return "redirect:/home";
		    }

		    usuarioServicio.eliminarUsuarioPorId(id);

		    return "redirect:/admin/usuarios";
		}
		
		// Para cerrar sesion 
		@GetMapping("/logout")
		public String logout(HttpSession session) {

		    session.invalidate();

		    return "redirect:/login";
		}
		
	}

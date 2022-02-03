package com.example.funda.controlador;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.funda.modelo.Administrador;
import com.example.funda.modelo.Formulario;
import com.example.funda.modelo.PreguntaUno;
import com.example.funda.modelo.RespuestaUno;
import com.example.funda.modelo.Usuario;
import com.example.funda.repositorio.PreguntaUnoDao;
import com.example.funda.repositorio.RespuestaUnoDao;
import com.example.funda.repositorio.UsuarioDao;
import com.example.funda.util.UserAutenticado;

@Controller
public class RespuestaControlador {
	
	@Autowired
	private PreguntaUnoDao daoPreguntaUno;
	
	@Autowired
	private RespuestaUnoDao daoRespuestaUno;
	
	@Autowired
	private UserAutenticado userAutenticado;
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	
	
	@GetMapping("/ResponderFormulario")
	public String responderFormulario(Model model) {
		try {
			System.out.println("responder formulario inicio");
			UserDetails user = userAutenticado.getAuth();
			Usuario usuario = usuarioDao.findByCorreo(user.getUsername());
			RespuestaUno runo = new RespuestaUno();
			List<PreguntaUno> lis = daoPreguntaUno.findAll();
			if (lis.size() > 0) {
				model.addAttribute("puno", lis.get(0));
				System.out.println("id pregunta =" +lis.get(0).getPregunta());
			}else {
				
			}
			model.addAttribute("iduser", usuario.getId());
			model.addAttribute("runo", runo);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "respuestaUno";
	}
	
	@PostMapping("/guardarRespuestaUno/{id}")
	public String guardarRespuestaUno(@Valid RespuestaUno runo, BindingResult result, Model model, @PathVariable("id") int id) {
		System.out.println("inicio guardar respuesta uno");
		List<PreguntaUno> lis = daoPreguntaUno.findAll();
		PreguntaUno pu = null;
		if (lis.size() > 0) {
			pu = lis.get(0);
			model.addAttribute("puno", pu);
		}
		if (result.hasErrors()) {
			model.addAttribute("iduser", id);
			model.addAttribute("runo", runo);
			return "preguntaUno";
		} else {
			try {
				if (runo.getOpcion() == 1) {
					runo.setRespuesta(pu.getRespuestaUno());
					if (pu.getRespuestaUnov() == true) {
						runo.setResultado(true);
					} else {
						runo.setResultado(false);
					}
				}else if (runo.getOpcion() == 2) {
					runo.setRespuesta(pu.getRespuestaDos());
					if (pu.getRespuestaDosv() == true) {
						runo.setResultado(true);
					} else {
						runo.setResultado(false);
					}
				}else if (runo.getOpcion() == 3) {
					runo.setRespuesta(pu.getRespuestaTres());
					if (pu.getRespuestaTresv() == true) {
						runo.setResultado(true);
					} else {
						runo.setResultado(false);
					}
				}else {
					model.addAttribute("iduser", id);
					model.addAttribute("runo", runo);
					model.addAttribute("error", "debe escojer una de las opciones de los radio button para escojer la respuesta");
					return "preguntaUno";
				}
				Usuario usua = usuarioDao.findById(id).get();
				runo.setUsuario(usua);
				daoRespuestaUno.save(runo);
				System.out.println("fin guardar respuesta uno");
			} catch (Exception e) {
				System.out.println(e.getMessage());
				model.addAttribute("error", "error al guardar base de datos la respuesta uno");
				return "registro";
			}
		}
		return "redirect:/menu";
	}
}

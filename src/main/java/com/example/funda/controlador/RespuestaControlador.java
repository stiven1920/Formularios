package com.example.funda.controlador;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.funda.modelo.Formulario;
import com.example.funda.modelo.PreguntaDos;
import com.example.funda.modelo.PreguntaUno;
import com.example.funda.modelo.RespuestaDos;
import com.example.funda.modelo.RespuestaUno;
import com.example.funda.modelo.Usuario;
import com.example.funda.repositorio.FormularioDao;
import com.example.funda.repositorio.PreguntaDosDao;
import com.example.funda.repositorio.PreguntaUnoDao;
import com.example.funda.repositorio.RespuestaDosDao;
import com.example.funda.repositorio.RespuestaUnoDao;
import com.example.funda.util.ImageUtil;
import com.example.funda.util.UserAutenticado;

@Controller
public class RespuestaControlador {

	@Autowired
	private PreguntaUnoDao daoPreguntaUno;

	@Autowired
	private RespuestaUnoDao daoRespuestaUno;

	@Autowired
	private PreguntaDosDao daoPreguntaDos;

	@Autowired
	private RespuestaDosDao daoRespuestaDos;

	@Autowired
	private UserAutenticado userAutenticado;

	@Autowired
	private FormularioDao formularioDao;

	@GetMapping("/ResponderFormulario/{tipo}")
	public String responderFormulario(Model model, @PathVariable("tipo") int tipo) {
		try {
			Formulario fo;
			System.out.println("responder formulario inicio");
			RespuestaUno runo = new RespuestaUno();

			if (tipo == 1) {
				fo = formularioDao.findByPrevios(true);
			} else {
				fo = formularioDao.findByPrevios(false);
			}

//		System.out.print("condicion 1 respuesta: "+daoRespuestaUno.findByUsuarioAndPreguntaUno(userAutenticado.getUser(), fo.getPreguntaUnoB().get(0)).toString());
			boolean condicion = false;
			if (daoRespuestaUno.findByUsuarioAndPreguntaUno(userAutenticado.getUser(),
					fo.getPreguntaUnoB().get(0)) != null) {
				if (fo.getId() == daoRespuestaUno
						.findByUsuarioAndPreguntaUno(userAutenticado.getUser(), fo.getPreguntaUnoB().get(0))
						.getFormulario().getId()) {
					return "redirect:/menu/0";
				} else {
					condicion = true;
				}

			} else {
				condicion = true;
			}
			if (condicion == true) {
				// cargamos la primera pregunta a responder
				List<PreguntaUno> lis = fo.getPreguntaUnoB();
				if (lis.size() > 0) {
					;
					model.addAttribute("puno", lis.get(0));
					model.addAttribute("idPuno", lis.get(0).getId());
					System.out.println("id pregunta =" + lis.get(0).getId());
				}
				model.addAttribute("runo", runo);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "respuestaUno";
	}

	@PostMapping("/guardarRespuestaUno/{idPuno}")
	public String guardarRespuestaUno(@Valid RespuestaUno runo, BindingResult result, Model model,
			@PathVariable("idPuno") int idPuno) {
		System.out.println("inicio guardar respuesta uno");
		// pregunta que se respondio
		PreguntaUno pu = daoPreguntaUno.findById(idPuno).get();
		model.addAttribute("puno", pu);
		// formulario al que pertenece la pregunta
		Formulario fo = formularioDao.buscarPorPreguntaUno(pu);
		System.out.println("idformu " + fo.getId());
		// usuario que esta respondiendo el formulario
		Usuario usuario = userAutenticado.getUser();
		if (result.hasErrors()) {
			model.addAttribute("runo", runo);
			return "respuestaUno";
		} else {
			try {
				if (runo.getOpcion() == 1) {
					runo.setRespuesta(pu.getRespuestaUno());
					if (pu.getRespuestaUnov() == true) {
						runo.setResultado(true);
					} else {
						runo.setResultado(false);
					}
				} else if (runo.getOpcion() == 2) {
					runo.setRespuesta(pu.getRespuestaDos());
					if (pu.getRespuestaDosv() == true) {
						runo.setResultado(true);
					} else {
						runo.setResultado(false);
					}
				} else if (runo.getOpcion() == 3) {
					runo.setRespuesta(pu.getRespuestaTres());
					if (pu.getRespuestaTresv() == true) {
						runo.setResultado(true);
					} else {
						runo.setResultado(false);
					}
				} else {
					model.addAttribute("runo", runo);
					model.addAttribute("error",
							"debe escojer una de las opciones de los radio button para escojer la respuesta");
					return "respuestaUno";
				}
				runo.setUsuario(usuario);
				runo.setFormulario(fo);
				runo.setPreguntaUno(pu);
				daoRespuestaUno.save(runo);
				if (fo.isPrevios() == true) {
					boolean opcion = false;
					if (fo.getPreguntaUnoB().get(0).getId() == idPuno) {
						pu = fo.getPreguntaUnoB().get(1);
						opcion = true;
					} else if (fo.getPreguntaUnoB().get(1).getId() == idPuno) {
						pu = fo.getPreguntaUnoB().get(2);
						opcion = true;
					} else if (fo.getPreguntaUnoB().get(2).getId() == idPuno) {
						return "redirect:/menu/0";
					}

					if (opcion == true) {
						runo = new RespuestaUno();
						model.addAttribute("runo", runo);
						model.addAttribute("puno", pu);
						model.addAttribute("idPuno", pu.getId());
						model.addAttribute("idFormu", fo.getId());
						return "respuestaUno";
					}
				} else {
					System.out.println("cargando pregunta dos");
					RespuestaDos rdos = new RespuestaDos();
					PreguntaDos pdos = fo.getPreguntaDosB();
					model.addAttribute("imgUtil", new ImageUtil());
					model.addAttribute("pdos", pdos);
					model.addAttribute("rdos", rdos);
					model.addAttribute("idPdos", pdos.getId());
				}
				System.out.println("fin guardar respuesta uno");
			} catch (Exception e) {
				System.out.println(e.getMessage());
				model.addAttribute("error", "error al guardar base de datos la respuesta uno");
				return "respuestaUno";
			}
		}
		return "respuestaDos";
	}

	@PostMapping("/guardarRespuestaDos/{idPdos}")
	public String guardarRespuestaDos(@Valid RespuestaDos rdos, BindingResult result, Model model,
			@PathVariable("idPdos") int idPdos) {
		System.out.println("inicio guardar respuesta uno");
		// pregunta que se respondio
		PreguntaDos pd = daoPreguntaDos.findById(idPdos).get();
		// formulario al que pertenece la pregunta
		Formulario fo = formularioDao.buscarPorPreguntaDos(pd);
		System.out.println("idformu " + fo.getId());
		// usuario que esta respondiendo el formulario
		Usuario usuario = userAutenticado.getUser();
		if (result.hasErrors()) {
			model.addAttribute("pdos", pd);
			model.addAttribute("idPdos", pd.getId());
			model.addAttribute("rdos", rdos);
			return "respuestaDos";
		} else {
			try {
				rdos.setFormulario(fo);
				rdos.setPreguntaDos(pd);
				rdos.setUsuario(usuario);
				if (rdos.getRu().equals("1")) {
					rdos.setResultadoUno(true);
				}

				if (rdos.getRd().equals("2")) {
					rdos.setResultadoDos(true);
				}

				if (rdos.getRt().equals("3")) {
					rdos.setResultadoTres(true);
				}
				daoRespuestaDos.save(rdos);
				System.out.println("fin guardar respuesta dos");
			} catch (Exception e) {
				System.out.println(e.getMessage());
				model.addAttribute("error", "error al guardar base de datos la respuesta dos");
				return "respuestaDos";
			}
		}
		return "redirect:/menu/0";
	}
}

package com.example.funda.controlador;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.funda.modelo.Formulario;
import com.example.funda.modelo.PreguntaDos;
import com.example.funda.modelo.PreguntaUno;
import com.example.funda.repositorio.FormularioDao;
import com.example.funda.repositorio.PreguntaDosDao;
import com.example.funda.repositorio.PreguntaUnoDao;

@Controller
public class PreguntaControlador {

	@Autowired
	private FormularioDao daoFormulario;

	@Autowired
	private PreguntaUnoDao daoPreguntaUno;

	@Autowired
	private PreguntaDosDao daoPreguntaDos;

	@PostMapping("/guardarPreguntaUno/{id}")
	public String guardarPreguntaUno(@Valid PreguntaUno puno, BindingResult result, Model model,
			@PathVariable("id") int id) {
		puno.setId(0);
		System.out.println(" "+puno.toString());
		if (result.hasErrors()) {
			model.addAttribute("idformu", id);
			model.addAttribute("puno", puno);
			return "preguntaUno";
		} else {
			model.addAttribute("idformu", id);
			try {
				System.out.println("resulradio = " + puno.getOpcion());
				if (puno.getOpcion() == 1) {
					puno.setRespuestaUnov(true);
				} else if (puno.getOpcion() == 2) {
					puno.setRespuestaDosv(true);
				} else if (puno.getOpcion() == 3) {
					puno.setRespuestaTresv(true);
				} else {
					model.addAttribute("error",
							"debe escojer una de las opciones de los radio button para completar la pregunta");
					model.addAttribute("puno", puno);
					return "preguntaUno";
				}
				System.out.println("idFormu = " + id);
				Formulario formu = daoFormulario.findById(id).get();
				
				//guarda la pregunta
				puno = daoPreguntaUno.save(puno);
				
				//condiciones cuando se va crear un formulario de conocimientos basicos de solo 
				//guardar preguntas de tipo uno
				if(formu.isPrevios() == true){
					List<PreguntaUno> lis = formu.getPreguntaUnoB();
					lis.add(puno);
					formu.setPreguntaUnoB(lis);
					daoFormulario.save(formu);
					if (lis.size() >= 3) {
						return "redirect:/menu";
					} else {
						model.addAttribute("idformu", id);
						PreguntaUno pre = new PreguntaUno();
						pre.setId(0);
						model.addAttribute("puno", pre);
						return "preguntaUno";
					}
					
				}else {
					List<PreguntaUno> lis = new ArrayList<PreguntaUno>();
					lis.add(puno);
					formu.setPreguntaUnoB(lis);
					daoFormulario.save(formu);
					
					//cargamos en el model el otro tipo de pregunta
					PreguntaDos pre = new PreguntaDos();
					model.addAttribute("pdos", pre);
				}
				
			} catch (Exception e) {
				model.addAttribute("idformu", id);
				model.addAttribute("puno", puno);
				System.out.println(e.getMessage());
				model.addAttribute("error", "error al guardar base de datos la pregunta uno");
				return "preguntaUno";
			}
		}
		return "preguntaDos";
	}

	@PostMapping("/guardarPreguntaDos/{id}")
	public String guardarPreguntaDos(@Valid PreguntaDos pdos, @PathVariable("id") int id, BindingResult result,
			Model model, final @RequestParam("ru") MultipartFile fileru, final @RequestParam("rd") MultipartFile filerd,
			final @RequestParam("rt") MultipartFile filert) {

		if (result.hasErrors()) {
			model.addAttribute("idformu", id);
			model.addAttribute("pdos", pdos);
			return "preguntaDos";
		}

		try {
			System.out.println("inf: " + pdos.getInformacion());

			// formulario al que pertenece la pregunta dos
			System.out.println("idFormu = " + id);
			Formulario formu = daoFormulario.findById(id).get();

			// guarda la preguntados
			pdos.setRespuestaUno(fileru.getBytes());
			pdos.setRespuestaDos(filerd.getBytes());
			pdos.setRespuestaTres(filert.getBytes());
			List<Formulario> lis = new ArrayList<Formulario>();
			lis.add(formu);
			pdos.setFormulario(lis);
			pdos = daoPreguntaDos.save(pdos);
			formu.setPreguntaDosB(pdos);
			daoFormulario.save(formu);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/menu";
	}

	@GetMapping("/listFormulario")
	public String listAeronave(Model model) {
		model.addAttribute("list", daoFormulario.findAll());
		return "listaPreguntas";
	}
}
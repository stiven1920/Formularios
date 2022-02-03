package com.example.funda.controlador;

import java.util.ArrayList;
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
import com.example.funda.modelo.PreguntaUno;
import com.example.funda.repositorio.FormularioDao;
import com.example.funda.repositorio.PreguntaUnoDao;

@Controller
public class PreguntaControlador {
	
	@Autowired
	private FormularioDao daoFormulario;

	@Autowired
	private PreguntaUnoDao daoPreguntaUno;

	@PostMapping("/guardarPreguntaUno/{id}")
	public String guardarPreguntaUno(@Valid PreguntaUno puno, BindingResult result, Model model, @PathVariable("id") int id) {
		//System.out.println("idFormu = "+puno.getFormularios().get(0).getId());
		if (result.hasErrors()) {
			model.addAttribute("idformu", id);
			model.addAttribute("preguntaUno", puno);
			return "preguntaUno";
		} else {
			try {
				System.out.println("resulradio = " + puno.getOpcion());
				if (puno.getOpcion() == 1) {
					puno.setRespuestaUnov(true);
				}else if (puno.getOpcion() == 2) {
					puno.setRespuestaDosv(true);
				}else if (puno.getOpcion() == 3) {
					puno.setRespuestaTresv(true);
				}else {
					model.addAttribute("idformu", id);
					model.addAttribute("error", "debe escojer una de las opciones de los radio button para completar la pregunta");
					model.addAttribute("preguntaUno", puno);
					return "preguntaUno";
				}
				System.out.println("idFormu = "+id);
				Formulario formu = daoFormulario.findById(id).get();
				List<Formulario> lis = new ArrayList<Formulario>();
				lis.add(formu);
				puno.setFormulario(lis);
				daoPreguntaUno.save(puno);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				model.addAttribute("error", "error al guardar base de datos la pregunta uno");
				return "registro";
			}
		}
		return "redirect:/menu";
	}
	
	@GetMapping("/listFormulario")
	public String listAeronave(Model model) {
		model.addAttribute("list", daoFormulario.findAll());
		return "listaPreguntas";
		
	}
	
}

package com.example.funda.controlador;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.funda.modelo.Administrador;
import com.example.funda.modelo.Formulario;
import com.example.funda.modelo.PreguntaUno;
import com.example.funda.repositorio.AdministradorDao;
import com.example.funda.repositorio.FormularioDao;
import com.example.funda.util.UserAutenticado;

@Controller
public class FormularioController {
	
	@Autowired
	private FormularioDao daoFormulario;
	
	@Autowired
	private AdministradorDao administradorDao;
	
	@Autowired
	private UserAutenticado userAutenticado;
	
	@GetMapping("formulario")
    public String formlario1(Model model) {
		model.addAttribute("formulario1", new PreguntaUno());
		model.addAttribute("formulario1", daoFormulario.findAll());
		return "formulario1";
	}
	
	@GetMapping("/crearFormulario")
	public String guardarFormulario(Model model) {
		try {
			Formulario formu = new Formulario();
			UserDetails user = userAutenticado.getAuth();
			Administrador admin = administradorDao.findByCorreo(user.getUsername());
			formu.setAdministradorBean(admin);
			formu = daoFormulario.save(formu);
			System.out.println("F"+formu.getId());
			model.addAttribute("idformu", formu.getId());
			PreguntaUno pre = new PreguntaUno();
			List<Formulario> lis = new ArrayList(); //prueba
			lis.add(formu); //prueba
			pre.setFormularios(lis); //prueba
			model.addAttribute("puno", pre);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return "preguntaUno";
	}
}

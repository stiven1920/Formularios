package com.example.funda.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
	
	
	
	@GetMapping("/crearFormulario/{tipo}")
	public String guardarFormulario(Model model, @PathVariable("tipo") int tipo) {
		try {
			Formulario formu = new Formulario();
			UserDetails user = userAutenticado.getAuth();
			Administrador admin = administradorDao.findByCorreo(user.getUsername());
			formu.setAdministradorBean(admin);
			if(tipo == 1) {
				formu.setPrevios(true);
			}
			formu = daoFormulario.save(formu);
			System.out.println("F"+formu.getId());
			model.addAttribute("idformu", formu.getId());
			PreguntaUno pre = new PreguntaUno();
			model.addAttribute("puno", pre);
			model.addAttribute("tipo", tipo);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "preguntaUno";
	}
}

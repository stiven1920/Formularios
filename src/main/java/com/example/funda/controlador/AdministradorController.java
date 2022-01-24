package com.example.funda.controlador;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.funda.modelo.Administrador;
import com.example.funda.modelo.Authority;
import com.example.funda.repositorio.AdministradorDao;
import com.example.funda.repositorio.AuthorityDao;
import com.example.funda.util.Passgenerator;

@Controller
public class AdministradorController {
	
	@Autowired
	private AdministradorDao administradorDao;
	
	@Autowired
	private AuthorityDao authorityDao;
	
	@Autowired
	private Passgenerator passgenerator;
	
	//este metodo n
    @PostMapping("/admin/guardarAdmin")
	public String guardar(@Valid Administrador admin, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("admin", admin);
			model.addAttribute("administradores", administradorDao.findAll());
			return "gestionAdministrador";
		}
		try {
			if (valPass(admin.getContrasena())) {
				if(valExitencia(admin.getCorreo())) {
					if(valTelefonoRepetido(admin.getTelefono())) {
						Authority autorizacion = authorityDao.findByAuthority("ROLE_ADMIN");
						Set<Authority> authority = new HashSet<Authority>();
						authority.add(autorizacion);
						admin.setAuthority(authority);
						admin.setContrasena(passgenerator.enciptarPassword(admin.getContrasena()));
						administradorDao.save(admin);
						model.addAttribute("admin", new Administrador());
						model.addAttribute("administradores", authorityDao.findAll());
						return "redirect:/admin/gestionAdministrador";
					}else {
						model.addAttribute("admin", admin);
						model.addAttribute("administradores", authorityDao.findAll());
						model.addAttribute("error", "Este telefono ya esta en uso.");
						return "gestionAdministrador";
					}
				}else {
					model.addAttribute("admin", admin);
					model.addAttribute("administradores", administradorDao.findAll());
					model.addAttribute("error", "Ya hay un usuario registrado por este correo.");
					return "gestionAdministrador";
				}
			} else {
				model.addAttribute("admin", admin);
				model.addAttribute("administradores", administradorDao.findAll());
				model.addAttribute("error",
						"La contrasena debe contener minimo una mayuscula, una minuscula, un numero y un tamano mayor o igual a 6.");
				return "gestionAdministrador";
			}
		} catch (Exception e) {
			model.addAttribute("admin", admin);
			model.addAttribute("administradores", administradorDao.findAll());
			model.addAttribute("error", "error al guardar base de datos");
			return "gestionAdministrador";
		}
	}
    
    private boolean valTelefonoRepetido(String telefono) {
		if(administradorDao.findByTelefono(telefono)==null) {
			return true;
		}else {
			return false;
		}
	}

	private boolean valExitencia(String correo) {
		if(administradorDao.findByCorreo(correo)==null) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean valPass(String pass) {
		boolean hasCap = false;
		boolean hasLow = false;
		boolean hasNum = false;
		char c;
		for (int i = 0; i < pass.length(); i++) {
			c = pass.charAt(i);
			if (Character.isDigit(c)) {
				hasNum = true;
			} else if (Character.isUpperCase(c)) {
				hasCap = true;
			} else if (Character.isLowerCase(c)) {
				hasLow = true;
			}
			if (hasCap && hasLow && hasNum) {
				return true;
			}
		}
		return false;
	}
}

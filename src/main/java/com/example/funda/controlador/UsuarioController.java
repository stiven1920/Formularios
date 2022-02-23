package com.example.funda.controlador;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.funda.modelo.Administrador;
import com.example.funda.modelo.Authority;
import com.example.funda.modelo.Formulario;
import com.example.funda.modelo.Usuario;
import com.example.funda.repositorio.AdministradorDao;
import com.example.funda.repositorio.AuthorityDao;
import com.example.funda.repositorio.FormularioDao;
import com.example.funda.repositorio.UsuarioDao;
import com.example.funda.util.Passgenerator;
import com.example.funda.util.UserAutenticado;

@Controller
public class UsuarioController {

	@Autowired
	private UsuarioDao usuarioDao;

	@Autowired
	private AdministradorDao administradorDao;

	@Autowired
	private FormularioDao formularioDao;

	@Autowired
	private AuthorityDao authorityDao;

	@Autowired
	private Passgenerator passgenerator;

	@Autowired
	private UserAutenticado userAutenticado;

	@GetMapping("/")
	public String index(Model model) {
		return "index";
	}

	@GetMapping("/signup")
	public String signup(Model model) {
		return "login";
	}
	
	@GetMapping("/login")
	public String login(Model model) {
		return "login";
	}

	@GetMapping("/registrarse")
	public String registrarse(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "registro";
	}

	@GetMapping("/menu")
	public String menuV(Model model) {
		System.out.print("menuniar");
		String url = "";
		UserDetails user = userAutenticado.getAuth();
		for (GrantedAuthority grantedAuthority : user.getAuthorities()) {
			if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
				Formulario fo=  formularioDao.findByPrevios(true);
				if (fo != null) {
					url = "redirect:/ResponderFormulario/1";
				}else {
					url = "menu";
				}
			} else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
				url = "Admin";
			}
		}
		return url;
	}
	
	@GetMapping("/menu/{opcion}")
	public String menu(Model model,@PathVariable("opcion") int opcion) {
		System.out.print("menuniar");
		String url = "";
		UserDetails user = userAutenticado.getAuth();
		for (GrantedAuthority grantedAuthority : user.getAuthorities()) {
			if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
				Formulario fo=  formularioDao.findByPrevios(true);
				url = "menu";
			} else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
				url = "Admin";
			}
		}
		return url;
	}

	@PostMapping("/guardarUsuario")
	public String guardar(@Valid Usuario usuario, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "registro";
		}
		try {
			if (validarTipoContrasenaCorrecta(usuario.getContrasena())) {
				if (validarCorreoRepetido(usuario.getCorreo()) && validarCorreoRepetidoAdmin(usuario.getCorreo())) {
					if (validarTelefonoRepetido(usuario.getTelefono())
							&& validarTelefonoRepetidoAdmin(usuario.getTelefono())) {
						System.out.print("codigo" + usuario.getCodigo());
						if (usuario.getCodigo().equals("9080")) {
							Administrador admin = new Administrador(usuario);
							Authority autorizacion = authorityDao.findByAuthority("ROLE_ADMIN");
							Set<Authority> authority = new HashSet<Authority>();
							authority.add(autorizacion);
							admin.setAuthority(authority);
							admin.setContrasena(passgenerator.enciptarPassword(admin.getContrasena()));
							administradorDao.save(admin);
						} else if (usuario.getCodigo().equals("0000")) {
							Authority autorizacion = authorityDao.findByAuthority("ROLE_USER");
							Set<Authority> authority = new HashSet<Authority>();
							authority.add(autorizacion);
							usuario.setAuthority(authority);
							usuario.setContrasena(passgenerator.enciptarPassword(usuario.getContrasena()));
							usuarioDao.save(usuario);
						} else {
							model.addAttribute("error", "error ingrese un codigo correcto");
							return "registro";
						}
					} else {
						model.addAttribute("error", "Este telefono ya esta en uso.");
						return "registro";
					}
				} else {
					model.addAttribute("error", "Ya hay un usuario registrado con este correo.");
					return "registro";
				}
			} else {
				model.addAttribute("errorContrasena",
						"La contrasena debe contener minimo una mayuscula, una minuscula, un numero y un tamano mayor o igual a 6.");
				return "registro";
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.addAttribute("error", "error al guardar base de datos");
			return "registro";
		}
		return "redirect:/";
	}

	private boolean validarTelefonoRepetido(String telefono) {
		if (usuarioDao.findByTelefono(telefono) == null) {
			return true;
		} else {
			return false;
		}
	}

	private boolean validarCorreoRepetido(String correo) {
		if (usuarioDao.findByCorreo(correo) == null) {
			return true;
		} else {
			return false;
		}
	}

	private boolean validarTelefonoRepetidoAdmin(String telefono) {
		if (administradorDao.findByTelefono(telefono) == null) {
			return true;
		} else {
			return false;
		}
	}

	private boolean validarCorreoRepetidoAdmin(String correo) {
		if (administradorDao.findByCorreo(correo) == null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean validarTipoContrasenaCorrecta(String pass) {
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

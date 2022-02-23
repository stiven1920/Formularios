package com.example.funda.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.funda.modelo.Usuario;
import com.example.funda.repositorio.UsuarioDao;

@Service
public class UserAutenticado {
	
	@Autowired
	private UsuarioDao usuarioDao;

	public UserDetails getAuth() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return (UserDetails) auth.getPrincipal();
	}
	
	public Usuario getUser() {
		UserDetails user = getAuth();
		Usuario usuario = usuarioDao.findByCorreo(user.getUsername());
		return usuario;
	}
	
}
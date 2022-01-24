package com.example.funda.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.funda.modelo.Administrador;
import com.example.funda.modelo.Authority;
import com.example.funda.modelo.Usuario;
import com.example.funda.repositorio.AdministradorDao;
import com.example.funda.repositorio.UsuarioDao;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    UsuarioDao usuarioRepo;
    
    @Autowired
    AdministradorDao adminRepo;
	
    @Override
     public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
    	System.out.println("user details");
    	UserDetails user=null;
    	List grantList = new ArrayList();
    	Administrador admin = adminRepo.findByCorreo(correo);
    	Usuario usuario = usuarioRepo.findByCorreo(correo);
    	
    	if (admin != null) {
    		for (Authority authority: admin.getAuthority()) {
    	        // ROLE_USER, ROLE_ADMIN,..
    	        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getAuthority());
    	        grantList.add(grantedAuthority);
    	    }
    		user = (UserDetails) new User(admin.getCorreo(), admin.getContrasena(), grantList);
    		System.out.println("admin");
		} else if(usuario != null) {
			for (Authority authority: usuario.getAuthority()) {
		        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getAuthority());
		        grantList.add(grantedAuthority);
		    }
			System.out.println("user");
			user = (UserDetails) new User(usuario.getCorreo(), usuario.getContrasena(), grantList);
		}
    	
    	if (user != null) {
			return user;
		}else {
			System.out.println("error al logiar");
			throw new UsernameNotFoundException("No existe usuario");
		}
    }
}

package com.example.funda.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserAutenticado {

	public UserDetails getAuth() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return (UserDetails) auth.getPrincipal();
	}
}

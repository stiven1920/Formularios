package com.example.funda.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class Passgenerator {

	public static void main(String[] args) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
		System.out.println(bCryptPasswordEncoder.encode("123"));
		
	}

	public String enciptarPassword(String cadenaPass) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
		return bCryptPasswordEncoder.encode(cadenaPass);
	}

}

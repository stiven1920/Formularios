package com.example.funda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.funda.service.UserDetailsServiceImpl;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MainSecurity extends WebSecurityConfigurerAdapter {

	String[] resources = new String[] { "/include/**", "/css/**", "/icons/**", "/imagenes/**", "/js/**", "/layer/**" };
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		System.out.print("redi");
		http.authorizeRequests().antMatchers(resources).permitAll().antMatchers("/","/signup", "/registrarse", "/guardarUsuario").permitAll()
				.antMatchers("/admin*").access("hasRole('ADMIN') or hasRole('USER')")
				.antMatchers("/user*").access("hasRole('USER')")
				.anyRequest().authenticated().and().formLogin()
				.loginPage("/login").permitAll()
				.defaultSuccessUrl("/menu").failureUrl("/signup?error=true")
				.usernameParameter("correo").passwordParameter("contrasena").and().logout().permitAll()
				.logoutSuccessUrl("/signup?logout=true");
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
		return bCryptPasswordEncoder;
	}

	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
}
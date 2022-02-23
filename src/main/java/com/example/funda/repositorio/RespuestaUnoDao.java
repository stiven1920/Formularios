package com.example.funda.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.funda.modelo.PreguntaUno;
import com.example.funda.modelo.RespuestaUno;
import com.example.funda.modelo.Usuario;

@Repository
public interface RespuestaUnoDao extends CrudRepository< RespuestaUno, Integer> {
		
	RespuestaUno findByUsuarioAndPreguntaUno(Usuario usuario,PreguntaUno preguntaUno);
	
}
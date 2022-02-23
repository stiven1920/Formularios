package com.example.funda.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.funda.modelo.Formulario;
import com.example.funda.modelo.PreguntaDos;
import com.example.funda.modelo.PreguntaUno;

@Repository
public interface FormularioDao extends CrudRepository<Formulario, Integer> {
	Formulario findByPrevios(boolean previos);
	
	@Query("Select f from Formulario f join f.preguntaUnoB p where p = ?1")
	Formulario buscarPorPreguntaUno(PreguntaUno pu);
	
	@Query("Select f from Formulario f join f.preguntaDosB p where p = ?1")
	Formulario buscarPorPreguntaDos(PreguntaDos pd);
}
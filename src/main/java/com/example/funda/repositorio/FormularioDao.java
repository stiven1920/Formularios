package com.example.funda.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.funda.modelo.Formulario;

@Repository
public interface FormularioDao extends CrudRepository<Formulario, Integer> {
	@Query(value = "select * from Usuario u join Formulario", nativeQuery = true)
	List<Formulario> getAll();
}
package com.example.funda.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.funda.modelo.Administrador;

@Repository
public interface AdministradorDao extends CrudRepository<Administrador, Integer>{
	Administrador findByCorreo(String correo);
	Administrador findByTelefono(String telefono);
}

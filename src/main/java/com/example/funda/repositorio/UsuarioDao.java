package com.example.funda.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.funda.modelo.Usuario;

@Repository
public interface UsuarioDao extends CrudRepository<Usuario, Integer> {
	Usuario findByCorreo(String correo);
	Usuario findByTelefono(String telefono);
}
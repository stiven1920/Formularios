package com.example.funda.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.funda.modelo.PreguntaDos;

@Repository
public interface PreguntaDosDao extends CrudRepository<PreguntaDos, Integer>{

}

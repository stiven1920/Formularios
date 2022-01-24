package com.example.funda.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.funda.modelo.PreguntaUno;

@Repository
public interface PreguntaUnoDao extends CrudRepository<PreguntaUno, Integer> {
}
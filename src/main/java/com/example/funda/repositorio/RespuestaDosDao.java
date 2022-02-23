package com.example.funda.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.funda.modelo.RespuestaDos;

@Repository
public interface RespuestaDosDao extends CrudRepository<RespuestaDos, Integer>{
}

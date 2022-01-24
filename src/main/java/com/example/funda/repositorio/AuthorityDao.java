package com.example.funda.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.funda.modelo.Authority;

@Repository
public interface AuthorityDao extends CrudRepository<Authority, Long>  {
    Authority findByAuthority(String authority);
}
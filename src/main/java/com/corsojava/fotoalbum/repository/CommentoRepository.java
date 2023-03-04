package com.corsojava.fotoalbum.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.corsojava.fotoalbum.model.Commento;

public interface CommentoRepository extends JpaRepository<Commento, Integer> {

}

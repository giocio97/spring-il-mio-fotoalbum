package com.corsojava.fotoalbum.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.corsojava.fotoalbum.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

}

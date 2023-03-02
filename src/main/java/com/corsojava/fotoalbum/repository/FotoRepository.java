package com.corsojava.fotoalbum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.corsojava.fotoalbum.model.Foto;

public interface FotoRepository extends JpaRepository<Foto, Integer> {

	public List<Foto> findByTitoloLike(String titolo);

	public List<Foto> findByTagLike(String tag);
}

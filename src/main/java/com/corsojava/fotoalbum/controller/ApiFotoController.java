package com.corsojava.fotoalbum.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.corsojava.fotoalbum.model.Commento;
import com.corsojava.fotoalbum.model.Foto;
import com.corsojava.fotoalbum.repository.CommentoRepository;
import com.corsojava.fotoalbum.repository.FotoRepository;

@RestController
@CrossOrigin
@RequestMapping("/api/foto")
public class ApiFotoController {
	@Autowired
	FotoRepository fotoRepo;

	@Autowired
	CommentoRepository comRepo;

	@GetMapping
	public List<Foto> index(@RequestParam(name = "search", required = false) String keyword) {
		List<Foto> photos;
		if (keyword == null) {
			photos = fotoRepo.findAll();
		} else {
			photos = fotoRepo.findByTitoloLikeOrTagLike("%" + keyword + "%", "%" + keyword + "%");
		}
		return (List<Foto>) photos.stream().filter(Foto::isVisibile).collect(Collectors.toList());
	}

	@GetMapping("{id}")
	public ResponseEntity<Foto> detail(@PathVariable("id") Integer id) {
		Optional<Foto> ph = fotoRepo.findById(id);
		if (ph.isPresent()) {
			return new ResponseEntity<Foto>(ph.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<Foto>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("{id}/comments")
	public List<Commento> showComments(@PathVariable(name = "id") Integer id) {
		List<Commento> comments = fotoRepo.getReferenceById(id).getCommenti();

		return comments;
	}

	@PostMapping("{id}/comments")
	public ResponseEntity<Commento> createComment(@PathVariable(name = "id") Integer id,
			@RequestBody Commento commento) {
		Optional<Foto> result = fotoRepo.findById(id);

		if (result.isPresent()) {
			commento.setFoto(result.get());
			comRepo.save(commento);
			return new ResponseEntity<Commento>(commento, HttpStatus.OK);
		} else
			return new ResponseEntity<Commento>(HttpStatus.NOT_FOUND);
	}

}

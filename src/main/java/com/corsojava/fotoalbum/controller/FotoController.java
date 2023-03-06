package com.corsojava.fotoalbum.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.corsojava.fotoalbum.model.Categoria;
import com.corsojava.fotoalbum.model.Foto;
import com.corsojava.fotoalbum.repository.CategoriaRepository;
import com.corsojava.fotoalbum.repository.FotoRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/foto")
public class FotoController {

	@Autowired
	FotoRepository fotoRepo;
	@Autowired
	CategoriaRepository cateRepo;

	@GetMapping
	public String index(@RequestParam(name = "search", required = false) String keyword, Model model) {

		List<Foto> elencoFoto;

		if (keyword == null) {
			elencoFoto = fotoRepo.findAll();
		} else {
			elencoFoto = fotoRepo.findByTitoloLikeOrTagLike("%" + keyword + "%", "%" + keyword + "%");

		}

		model.addAttribute("elencoFoto", elencoFoto);
		return "fotos/index";
	}

	@GetMapping("/{id}")
	public String detail(@PathVariable("id") Integer id, Model model) {

		Optional<Foto> f = fotoRepo.findById(id);
		if (f.isPresent()) {
			model.addAttribute("foto", f.get());
		} else {
			return "fotos/index";
		}
		return "fotos/detail";

	}

	@GetMapping("/create")
	public String create(Model model) {
		Foto foto = new Foto();
		List<Categoria> listaCategorie = cateRepo.findAll(Sort.by("nome"));
		model.addAttribute("listaCategorie", listaCategorie);
		model.addAttribute("foto", foto);

		return "fotos/create";
	}

	@PostMapping("/create")
	public String store(@Valid @ModelAttribute("foto") Foto formFoto, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors())
			return "fotos/create";

		fotoRepo.save(formFoto);
		return "redirect:/foto";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
		Foto foto;
		foto = fotoRepo.getReferenceById(id);
		List<Categoria> listaCategorie = cateRepo.findAll(Sort.by("nome"));
		model.addAttribute("listaCategorie", listaCategorie);
		model.addAttribute("foto", foto);
		return "fotos/edit";

	}

	@PostMapping("/edit/{id}")
	public String update(@Valid @ModelAttribute("foto") Foto formFoto, BindingResult bindingResult) {

		if (bindingResult.hasErrors())
			return "fotos/edit";

		fotoRepo.save(formFoto);
		return "redirect:/foto";
	}

	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id) {

		fotoRepo.deleteById(id);
		return "redirect:/foto";
	}

}

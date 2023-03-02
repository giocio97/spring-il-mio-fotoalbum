package com.corsojava.fotoalbum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.corsojava.fotoalbum.model.Categoria;
import com.corsojava.fotoalbum.repository.CategoriaRepository;

@Controller
@RequestMapping("/categorie")
public class CategoriaController {

	@Autowired
	CategoriaRepository cateRepo;

	@GetMapping()
	public String index(Model model) {
		List<Categoria> elencoCategorie = cateRepo.findAll(Sort.by("nome"));
		model.addAttribute("elencoCategorie", elencoCategorie);
		return "categorie/index";
	}

	@GetMapping("/create")
	public String create(Model model) {
		Categoria categoria = new Categoria();
		model.addAttribute("categoria", categoria);
		return "categorie/create";

	}

	@PostMapping("/create")
	public String store(@ModelAttribute("categoria") Categoria formCateoria, Model model) {

		cateRepo.save(formCateoria);
		return "redirect:/categorie";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
		Categoria categoria = cateRepo.getReferenceById(id);
		model.addAttribute("categoria", categoria);
		return "categorie/edit";

	}

	@PostMapping("/edit/{id}")
	public String update(@ModelAttribute("categoria") Categoria formCateoria) {
		cateRepo.save(formCateoria);
		return "redirect:/categorie";

	}

	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id) {
		cateRepo.deleteById(id);
		return "redirect:/categorie";
	}

}

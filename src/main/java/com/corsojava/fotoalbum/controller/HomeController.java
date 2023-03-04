package com.corsojava.fotoalbum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

	@GetMapping
	public String homepage() {

		return "frontend/home";
	}

	@GetMapping("/dettaglio")
	public String detail() {
		return "frontend/detail";
	}
}

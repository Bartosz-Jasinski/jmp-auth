package com.epam.mentoring.security.controller;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InfoController {
	private static final List<String> FUNFACTS = List.of(
			"A shrimp's heart is in its head.",
			"Rubber bands last longer when refrigerated.",
			"\"Dreamt\" is the only English word that ends in the letters \"mt\".",
			"Almonds are a member of the peach family.",
			"Most people fall asleep in seven minutes."
	);
	private final Random rand = new Random();

	@GetMapping("/info")
	public String info(Model model) {
		model.addAttribute("info", FUNFACTS.get(rand.nextInt(FUNFACTS.size())));
		return "info";
	}
}

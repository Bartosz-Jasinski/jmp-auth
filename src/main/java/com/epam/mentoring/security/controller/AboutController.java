package com.epam.mentoring.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {
	@GetMapping(path = "/about")
	public String about() {
		return "about";
	}
}

package com.epam.mentoring.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
	@GetMapping(path = "/admin")
	public String admin() {
		return "admin";
	}
}

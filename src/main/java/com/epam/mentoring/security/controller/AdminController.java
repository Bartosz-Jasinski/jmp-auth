package com.epam.mentoring.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.epam.mentoring.security.repository.UserRepository;

@Controller
public class AdminController {
	private final UserRepository userRepository;

	@Autowired
	public AdminController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@GetMapping(path = "/admin")
	public String admin(Model model) {
		model.addAttribute("users", userRepository.findUserByEnabledIsFalse());
		return "admin";
	}
}

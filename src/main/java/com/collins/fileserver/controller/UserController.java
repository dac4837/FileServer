package com.collins.fileserver.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.collins.fileserver.domain.User;

@Controller
public class UserController {
	

	@Autowired
	public UserController() {
		super();
	}
	
	@GetMapping("/login")
	public String getLogin(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}
	
	@GetMapping("/loginError")
	public String getLoginError(Model model) {
		System.out.println("Error logging in: " + model);
		model.addAttribute("loginError", true);
		return "login";
	}
	
	/*
	@PostMapping("/login")
	public String postLogin(@ModelAttribute @Valid String username , @ModelAttribute @Valid String password, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			return "login";
		}

		return "redirect:/files";
	}
	*/
	
	@PostMapping("/new_user")
	public String postNewUser(@ModelAttribute @Valid User user, BindingResult bindingResult, Model model) {
		
		return "files";
	}

}

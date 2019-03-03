package com.collins.fileserver.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.collins.fileserver.domain.UserDto;
import com.collins.fileserver.service.UserException;
import com.collins.fileserver.service.UserService;

@Controller
public class UserController {
	
	private final UserService userService;
	

	@Autowired
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	@GetMapping("/login")
	public String getLogin(Model model) {
		//model.addAttribute("user", new User());
		return "user/login";
	}
	
	@GetMapping("/loginError")
	public String getLoginError(Model model) {
		System.out.println("Error logging in: " + model);
		model.addAttribute("loginError", true);
		return "user/login";
	}
	
	@GetMapping("/register")
	public String getRegister(Model model) {
		model.addAttribute("user", new UserDto());
		return "user/newUser";
	}
	
	@PostMapping("/register")
	public String postRegister(@ModelAttribute("user") @Valid UserDto user, 
		      BindingResult result) {
		System.out.println(" registration");
		if (result.hasErrors()) {
			System.out.println("errors: " + result.getAllErrors());
			return "user/newUser";
		}
		try {
		userService.registerUser(user);
		} catch(UserException e) {
			result.rejectValue("username", "error.user", e.getMessage());
			return "user/newUser"; 
		}
		
		System.out.println("Finished registration");
		
		return "redirect:/register/success";
	}
	
	@GetMapping("/register/success")
	public String getRegisterSuccess(Model model) {
		
		return "/user/registerSuccess";
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
	


}

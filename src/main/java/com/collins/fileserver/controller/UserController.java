package com.collins.fileserver.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.collins.fileserver.domain.User;
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
		
		return "redirect:/register/success";
	}
	
	@GetMapping("/register/success")
	public String getRegisterSuccess(Model model) {
		
		return "user/registerSuccess";
	}
	
	@PreAuthorize("hasRole('PRIVILEGE_MANAGE_USERS')")
	@GetMapping("/users")
	public String getUsers(Model model) {
		
		List<User> users = userService.getUsers();
		model.addAttribute("users", users);
		
		return "user/users";
	}
	
	@GetMapping("/users/{username}")
	public String getUser(@PathVariable String username, Model model) {
		model.addAttribute("user", userService.getUser(username));
			
		return "user/editUser";
	}
	
	@GetMapping("/self")
	public String getCurrentUser(Model model, HttpServletRequest request) {
		
		model.addAttribute("user", getCurrentUser(request));
			
		return "user/editUser";
	}
	
	private User getCurrentUser(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		if(session == null) return null;
		Object sessionIdObject = session.getAttribute("userId");
		if(sessionIdObject == null || ! (sessionIdObject instanceof UUID) ) return null;
		UUID userId = (UUID) sessionIdObject;
		String s = userId.toString();

		return userService.getUserById(userId);
	}
	
}

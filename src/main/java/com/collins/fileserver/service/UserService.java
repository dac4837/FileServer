package com.collins.fileserver.service;

import java.util.List;
import java.util.UUID;

import org.h2.jdbc.JdbcSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.collins.fileserver.domain.Role;
import com.collins.fileserver.domain.User;
import com.collins.fileserver.domain.UserDto;
import com.collins.fileserver.repository.RoleRepository;
import com.collins.fileserver.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepositry;

	public User registerUser(UserDto user) {
		
		if (userRepository.findByUsername(user.getUsername()) != null) {
			throw new UserException ("Username already exists");
		}
		
		User registeredUser = new User();
		registeredUser.setDisplayName(user.getDisplayName());
		registeredUser.setPassword(passwordEncoder.encode(user.getPassword()));
		registeredUser.setUsername(user.getUsername());
		registeredUser.setRole(roleRepositry.findByName("ROLE_LOGIN"));
		
		try {
		userRepository.save(registeredUser);
		
		} catch (Exception e) {
			if(e instanceof JdbcSQLException)	throw new UserException ("Username already exists"); 
			else throw e;
		}
		
		return registeredUser;
	}
	
    public void deleteUser(final User user) {
        userRepository.delete(user);
    }
	
	
    public void saveRegisteredUser(final User user) {
        userRepository.save(user);
    }
    
    public User getUser(final String username) {
    	return userRepository.findByUsername(username);
    }
    
    public List<User> getUsers() {
    	return userRepository.findAll();
    }
    
    public User getUserById(UUID id) {
    	return userRepository.findById(id);
    }

	public void updateUserWithPassword(User user, final String password) {
		user.setPassword(passwordEncoder.encode(password));
		userRepository.save(user);
	}
	
	public void updateUserWithoutPassword(User user) {
		userRepository.save(user);
	}

	public Role getRole(String name) {
		return roleRepositry.findByName(name);
	}
	
	
}

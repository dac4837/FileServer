package com.collins.fileserver.service;

import org.h2.jdbc.JdbcSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.collins.fileserver.domain.User;
import com.collins.fileserver.domain.UserDto;
import com.collins.fileserver.repository.UserRepository;

@Service
public class UserService {
	
	private final UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public UserService(UserRepository userRepository) {
		super();

		this.userRepository = userRepository;
	}

	public User registerUser(UserDto user) {
		
		if (userRepository.findByUsername(user.getUsername()) != null) {
			throw new UserException ("Username already exists");
		}
		
		User registeredUser = new User();
		registeredUser.setDisplayName(user.getDisplayName());
		registeredUser.setPassword(passwordEncoder.encode(user.getPassword()));
		registeredUser.setUsername(user.getUsername());
		registeredUser.setRole("REGISTERED");
		
		try {
		userRepository.save(registeredUser);
		
		} catch (Exception e) {
			if(e instanceof JdbcSQLException)	throw new UserException ("Username already exists"); 
			else throw e;
		}
		
		return registeredUser;
	}
	

	public void changeUserPassword(final User user, final String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }
	
    public void deleteUser(final User user) {
        userRepository.delete(user);
    }
	
	
    public void saveRegisteredUser(final User user) {
        userRepository.save(user);
    }
	
	
}

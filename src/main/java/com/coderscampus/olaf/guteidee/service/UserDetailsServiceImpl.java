package com.coderscampus.olaf.guteidee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.coderscampus.olaf.guteidee.domain.User;
import com.coderscampus.olaf.guteidee.repository.UserRepository;
import com.coderscampus.olaf.guteidee.security.CustomSecuredUser;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);
		if (user == null) {
			System.out.println("user not found: " + user);
			throw new UsernameNotFoundException("incorrect credentials");
		}
		
		System.out.println("user found: " + user);
		return new CustomSecuredUser(user);
	}
	
}

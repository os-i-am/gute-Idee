package com.coderscampus.olaf.guteidee.security;

import org.springframework.security.core.userdetails.UserDetails;

import com.coderscampus.olaf.guteidee.domain.User;

public class CustomSecuredUser extends User implements UserDetails {

	private static final long serialVersionUID = -7608280334896306940L;
	
	public CustomSecuredUser() {}
	
	public CustomSecuredUser(User user) {
		this.setId(user.getId());
		this.setUsername(user.getUsername());
		this.setPassword(user.getPassword());
		this.setName(user.getName());
		this.setIdeas(user.getIdeas());
		this.setLikes(user.getLikes());
		this.setAuthorities(user.getAuthorities());
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}

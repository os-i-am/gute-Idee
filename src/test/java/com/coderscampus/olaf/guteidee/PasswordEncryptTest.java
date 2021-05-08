package com.coderscampus.olaf.guteidee;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

class PasswordEncryptTest {
	
	@Test
	void encryptPassword() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode("test");
		System.out.println(encodedPassword);
	}
	
	@Test
	void decryptPassword() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		boolean isPasswordMatches = encoder.matches(
		        "oss",
		        "$2a$10$lE6Yr/z5cBZHDqqYAAWqiut65PK1HlBAgqB6jOvLpkThZdBoVaNJi"
				);
		        System.out.println(isPasswordMatches);

	}
	
	
}

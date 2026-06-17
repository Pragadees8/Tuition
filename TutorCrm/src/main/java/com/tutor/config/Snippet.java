package com.tutor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

public class Snippet {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	
	    http
	        .csrf(csrf -> csrf.disable())
	        .authorizeHttpRequests(auth -> auth
	            // ✅ allow static files
	            .requestMatchers(
	                "/", 
	                "/notifications.html",
	                "/css/**",
	                "/js/**",
	                "/images/**",
	                "/ws/**"
	            ).permitAll()
	
	            // your APIs
	            .requestMatchers("/api/**").authenticated()
	
	            // everything else
	            .anyRequest().authenticated()
	        );
	
	    return http.build();
	}
	
}


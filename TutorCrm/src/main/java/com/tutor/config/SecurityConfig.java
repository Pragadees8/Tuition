package com.tutor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
				// ✅ Disable CSRF
				.csrf(AbstractHttpConfigurer::disable)

				// ✅ Enable CORS
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))

				// ✅ Stateless session (JWT)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				// ✅ Authorization Rules
				.authorizeHttpRequests(auth -> auth.requestMatchers("/api/admin/**").permitAll()
						.requestMatchers("/auth/**").permitAll().anyRequest().permitAll())

				// ✅ JWT Filter
				.addFilterBefore(new JwtValidator(), BasicAuthenticationFilter.class);

		return http.build();
	}

	// ✅ Proper CORS Configuration (Spring Boot 3)
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {

		CorsConfiguration configuration = new CorsConfiguration();

		// IMPORTANT: Use allowedOriginPatterns (NOT allowedOrigins)
		configuration.setAllowedOriginPatterns(Arrays.asList("http://localhost:5173", // Vite
				"http://localhost:3000" // React
		));

		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));

		configuration.setAllowedHeaders(Arrays.asList("*"));

		configuration.setAllowCredentials(true);

		configuration.setMaxAge(3600L);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		source.registerCorsConfiguration("/**", configuration);

		return source;
	}

	// ✅ Password Encoder
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
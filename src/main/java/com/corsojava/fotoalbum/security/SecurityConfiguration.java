package com.corsojava.fotoalbum.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf().disable().authorizeHttpRequests()

				// AUTH PER CREARE E MODIFICARE FOTO: ADMIN
				.requestMatchers("/foto/create", "/foto/edit", "/foto/edit/**").hasAuthority("ADMIN")

				// POST SU FOTO PER ELIMINARE UNA FOTO: ADMIN
				.requestMatchers(HttpMethod.POST, "/foto/**").hasAuthority("ADMIN")

				// CONTROLLO SU CATEGORIE : ADMIN
				.requestMatchers("/categorie", "/categorie/**", "/categorie/edit/**", "/categorie/create",
						"/categorie/edit")
				.hasAuthority("ADMIN")

				// ELENCO E DETTAGLIO PIZZE: ADMIN E USER
				.requestMatchers("/foto", "/foto/**").hasAnyAuthority("ADMIN")

				// ACCESSO ALLA HOME: USER ED ADMIN
				.requestMatchers("/**").permitAll().and().formLogin().and().logout().and().exceptionHandling();

		return http.build();

	}

	@Bean
	DatabaseUserDetailsService userDetailsService() {
		return new DatabaseUserDetailsService();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		System.out.println(passwordEncoder().encode("admin"));

		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

}

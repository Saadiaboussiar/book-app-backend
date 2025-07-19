package com.example.BookAppRestAPI;

import com.example.BookAppRestAPI.entites.Book;
import com.example.BookAppRestAPI.repositories.BookRepository;
import com.example.BookAppRestAPI.sec.entities.AppRole;
import com.example.BookAppRestAPI.sec.entities.AppUser;
import com.example.BookAppRestAPI.sec.repositories.AppRoleRepository;
import com.example.BookAppRestAPI.sec.repositories.AppUserRepository;
import com.example.BookAppRestAPI.sec.service.AccountService;
import com.example.BookAppRestAPI.sec.service.AccountServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class BookAppRestAPIApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookAppRestAPIApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}










}

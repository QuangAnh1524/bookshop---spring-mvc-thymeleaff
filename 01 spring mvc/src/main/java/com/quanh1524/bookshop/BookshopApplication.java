package com.quanh1524.bookshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class)
public class BookshopApplication {

	public static void main(String[] args) {

		SpringApplication.run(BookshopApplication.class, args);
	}

}

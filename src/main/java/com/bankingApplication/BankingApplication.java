package com.bankingApplication;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Banking Application",
				description = "Backend Rest APIs for Bank",
				version = "v1.0",
				contact = @Contact(
						name = "Anjan Phuyal",
						email = "anjanphuyal11@gmail.com",
						url = "https://github.com/AnjanP10/BankingApplication"
				),
				license = @License(
						name = "Anjan Phuyal",
						url = "https://github.com/AnjanP10/BankingApplication"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Banking Application Documentation",
				url = "https://github.com/AnjanP10/BankingApplication"
		))
public class BankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingApplication.class, args);
	}

}

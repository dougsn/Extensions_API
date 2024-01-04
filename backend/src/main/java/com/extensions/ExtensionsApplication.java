package com.extensions;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@SecurityScheme(name = "Bearer Authentication" , scheme = "bearer", type = SecuritySchemeType.HTTP, bearerFormat = "bearer")
@OpenAPIDefinition(
		info = @Info(
				title = "API REST Extensions",
				version = "1.0.0",
				description = "API REST Extensions",
				contact = @Contact(
						name = "Extensions"
				)
		)
)
public class ExtensionsApplication {

	public static void main(String[] args) {

		SpringApplication.run(ExtensionsApplication.class, args);
	}

}

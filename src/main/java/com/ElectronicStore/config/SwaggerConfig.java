package com.ElectronicStore.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

	 
	@Bean
	public OpenAPI openAPI() {

		String schemeName = "bearerScheme";

		return new OpenAPI()
				.addSecurityItem(new SecurityRequirement()
						.addList(schemeName)
						)
				.components(new Components().addSecuritySchemes(schemeName,
						new SecurityScheme().name(schemeName).type(SecurityScheme.Type.HTTP).bearerFormat("JWT")
								.scheme("bearer")))
				.info(new Info()
						.title("Electronic Store").
						description("Hello, This is my World !")
						.version("1.0.0")
						.contact(new Contact().name("Anjani").email("anjunagar2000@gmail.com")
								.url("https://www.youtube.com/watch?v=UvIWQSKz8kE&t=1s"))
						.license(new License().name("Apache"))

				).externalDocs(new ExternalDocumentation().url("https://www.youtube.com/watch?v=UvIWQSKz8kE&t=1s")
						.description("Hello , I m Good Person!"));
	}

	
	
	

}

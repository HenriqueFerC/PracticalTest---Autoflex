package com.example.demo.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(name = "Henrique Ferreira Cardoso", email = "henriqueferreiracardoso179411@hotmail.com"),
                title = "PracticalTest-Autoflex",
                description = "API for an industry focused on controlling products registration and raw materials inventory",
                version = "1.0"),
        servers = @Server(url = "http://localhost:8080")
)
public class OpenApiConfig {
}

package com.example.builder.landingbuilder;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Landing Builder API",
                version = "1.0",
                description = "API для конструктора лендингов"
        )
)
public class LandingBuilderApplication {

    public static void main(String[] args) {
        SpringApplication.run(LandingBuilderApplication.class, args);
    }

}

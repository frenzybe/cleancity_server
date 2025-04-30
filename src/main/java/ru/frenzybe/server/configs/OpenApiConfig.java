package ru.frenzybe.server.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                title = "CleanCity API",
                description = "Api для мобильного приложения CleanCity",
                version = "1.0.0",
                contact = @Contact(
                        name = "Mikhail Klimaka",
                        email = "mrcraftermiha@gmail.com"
                )
        )
)
@SecurityScheme(
        name = "JWT_Security",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenApiConfig {

}

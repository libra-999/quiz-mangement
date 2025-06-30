package org.example.tol.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI licenseAPI() {

        Contact contact = new Contact()
            .name("libra")
            .email("libra123@gmail.com")
            .url("http://libraBBH.com");
        License mitLicense = new License()
            .name("TOL System")
            .url("http://tol-system.com");
        Info info = new Info()
            .title("TOL API Document")
            .version("1.0")
            .contact(contact)
            .description("This API exposes endpoints to manage system for client.")
            .termsOfService("http://libraBBH.com/term")
            .license(mitLicense);

        return new OpenAPI().info(info);
    }
}

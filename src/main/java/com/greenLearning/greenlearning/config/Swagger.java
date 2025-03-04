package com.greenLearning.greenlearning.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Swagger {

    @Bean
    public OpenAPI openAPI(){

        return new OpenAPI()
                .info(new Info()
                        .title("Green-Learning")
                        .description("Projeto integrador semestral")
                        .version("1.0.0")
                        .contact(contact())
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://springdoc.org")));
    }

    public Contact contact(){
        Contact contact = new Contact();

        contact.setName("Pedro Henrique Vieira de Oliveira");
        contact.setUrl("https://github.com/PedroHenri1606");
        contact.setEmail("pedrohenri1606@gmail.com");

        return contact;
    }



}

package com.janfer.estoque.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI().components(new Components())
                .addServersItem(new Server().url("http://localhost:8080").description("Servidor local de desenvolvimento"))
                .addServersItem(new Server().url("http://estoque-production.up.railway.app").description("Servidor de produção"))
                .info(new Info()
                        .title("Gerenciamento de Estoque")
                        .description("API restful criado para facilitar o estoque de produtos.")
                        .version("v1.0.0"));
    }

}

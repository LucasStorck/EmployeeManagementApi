package com.lucas.employeeManagement.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
            .info(new Info()
                    .title("Gerenciador de Funcionários")
                    .version("v1.0")
                    .description("Sistema de Gerenciamento de Funcionários: Este sistema permite a gestão de dados de funcionários, oferecendo funcionalidades para criar, ler, atualizar e excluir (CRUD) informações relacionadas aos funcionários. O acesso completo a esses recursos é restrito aos usuários registrados com a permissão de 'SUPERUSER', garantindo que apenas administradores ou pessoas com privilégios elevados possam realizar essas operações sensíveis.")
            );
  }
}

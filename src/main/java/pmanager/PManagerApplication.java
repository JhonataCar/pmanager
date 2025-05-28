package pmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import pmanager.infrastucture.config.AppConfigProperties;

/*
Baseado na entidade project serve como exemplo principal para o gerenciamento de
recursos no sistema.

- ProjectRestResource.java: Controla as requisições HTTP para gerenciar projetos e chama o serviço apropriado para executar a lógica de negócios.
- ProjectService.java: Contém toda a lógica de negócios, como validação, criação, atualização, exclusão e carregamento de projetos.
- ProjectRepository.java: Interage com o banco de dados para realizar operações relacionadas à entidade `Project`.
- Project.java: Modelo da entidade "Projeto" usado pelo JPA/Hibernate para persistência de dados no banco.
- ProjectDTO.java: Modelo de transferência que simplifica a resposta HTTP enviada ao cliente.
- SecurityConfig.java: Garante que as requisições para os endpoints estejam protegidas por autenticação.
 */

@SpringBootApplication(
        exclude = { SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class }
)
@EnableConfigurationProperties(AppConfigProperties.class)
public class PManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PManagerApplication.class, args);
    }
}

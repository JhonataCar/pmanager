package pmanager.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pmanager.domain.entity.Project;

import java.util.Optional;

/*
O repositório que fornece acesso ao banco de dados.
Essa classe utiliza a interface JpaRepository do Spring Data JPA para realizar operações
com a tabela project.

- Salvar um novo projeto no banco.
- Buscar projetos por ID.
- Excluir projetos por ID.

 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {

    Optional<Project> findByName(String name);

}

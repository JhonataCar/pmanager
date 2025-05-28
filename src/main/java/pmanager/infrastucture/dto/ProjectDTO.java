package pmanager.infrastucture.dto;

import lombok.Data;
import pmanager.domain.entity.Member;
import pmanager.domain.entity.Project;
import pmanager.domain.model.ProjectStatus;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

/*
Essa classe é usada para **converter** a entidade Project em um
objeto que possa ser enviado como resposta (no formato JSON) para o cliente

- Recebe as informações básicas do projeto (id, name, description, etc.).
- Extrai os IDs dos membros associados ao projeto para simplificar a resposta HTTP, utilizando o método estático; `create()`.
- É usada em quase todos os métodos expostos pelo controlador REST (ProjectRestResource).

 */

@Data
public class ProjectDTO {
    private final String id;
    private final String name;
    private final String description;
    private final LocalDate initialDate;
    private final LocalDate finalData;
    private final ProjectStatus status;
    private final Set<String> memberIds;

    public static ProjectDTO create(Project project){
        return new ProjectDTO(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getInitialDate(),
                project.getFinalData(),
                project.getStatus(),
                Optional
                        .ofNullable(project.getMembers())
                        .orElse(List.of())
                        .stream()
                        .map(Member::getId)
                        .collect(toSet())
        );
    }
}

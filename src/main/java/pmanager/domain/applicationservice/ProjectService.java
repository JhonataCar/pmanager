package pmanager.domain.applicationservice;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pmanager.domain.entity.Member;
import pmanager.domain.entity.Project;
import pmanager.domain.exceptionDomain.DuplicateProjectException;
import pmanager.domain.exceptionDomain.InvalidProjectStatusException;
import pmanager.domain.exceptionDomain.ProjectNoFoundException;
import pmanager.domain.model.ProjectStatus;
import pmanager.domain.repository.ProjectRepository;
import pmanager.infrastucture.dto.SaveProjectDataDTO;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/*
- Cria; novos projetos com base nos dados validados recebidos do controlador (REST).
Aqui, chama o repositório de projetos para salvar o objeto no banco de dados.

- Carrega; projetos do banco por meio do repositório
ProjectRepository e lança exceções se o projeto não existir.

- Atualiza; o projeto, validando possíveis erros como duplicidade de nomes e
integridad dos dados.

- Exclui; projetos existentes no banco.
 */

//componente de serviço
@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectService {
    //info console:
    private final ProjectRepository projectRepository;
    private final MemberService memberService;

    /*transição unica
      Os dados são preenchidos com base no DTO recebido
     */
    @Transactional
    public Project createProject(SaveProjectDataDTO saveProjectData){
        if(existsProjectWithNames(saveProjectData.getName(), null)){
            //DuplicateProjectException`: Quando é detectado um nome de projeto duplicado.
            throw new DuplicateProjectException(saveProjectData.getName());
        }

        Project project = Project
                .builder()
                .name(saveProjectData.getName())
                .description(saveProjectData.getDescription())
                .initialDate(saveProjectData.getInitialDate())
                .finalData(saveProjectData.getFinalData())
                .status(ProjectStatus.PENDING)
                .build();

        //guarda no repositorio
        projectRepository.save(project);
        addMembersToProject(saveProjectData.getMemberIds(), project);
        log.info("Project created {}", project);
        return project;
    }

    //buscar projetos
    public Project loadProject(String projectId){
        return projectRepository
                    .findById(projectId)
                    .orElseThrow(() -> new ProjectNoFoundException(projectId));
    }

    //deletar
    @Transactional
    public void deleteProject(String projectId){
        Project project = loadProject(projectId);
        projectRepository.delete(project);
    }

    //Update
    @Transactional
    public Project updateProject(String projectId, SaveProjectDataDTO saveProjectData){
        if(existsProjectWithNames(saveProjectData.getName(), projectId)){
            throw new DuplicateProjectException(saveProjectData.getName());
        }

        Project project = loadProject(projectId);

        project.setName(saveProjectData.getName());
        project.setDescription(saveProjectData.getDescription());
        project.setInitialDate(saveProjectData.getInitialDate());
        project.setFinalData(saveProjectData.getFinalData());
        project.setStatus(convertToProjectStatus(saveProjectData.getStatus()));

        addMembersToProject(saveProjectData.getMemberIds(), project);

        return project;
    }

    private ProjectStatus convertToProjectStatus(String statusStr){
        try{
            return ProjectStatus.valueOf(statusStr);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidProjectStatusException(statusStr);
        }
    }

    //Nomes iguais
    private boolean existsProjectWithNames(String name, String idToExclude){
        return projectRepository
                .findByName(name)
                .filter(p -> !Objects.equals(p.getId(), idToExclude))
                .isPresent();
    }

    private void addMembersToProject(Set<String> memberIds, Project project){
        /*cria uma lista vazia, caso memberIds for null
          pega cada ID e retorna um membro associado.
        */
        List<Member> members = Optional
                .ofNullable(memberIds)
                .orElse(Set.of())
                .stream()
                .map(memberService::loadMemberById)
                .collect(toList());

        project.setMembers(members);
    }
}

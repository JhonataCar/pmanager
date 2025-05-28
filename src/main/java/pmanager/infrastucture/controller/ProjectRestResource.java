package pmanager.infrastucture.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pmanager.domain.applicationservice.ProjectService;
import pmanager.domain.entity.Project;
import pmanager.infrastucture.dto.ProjectDTO;
import pmanager.infrastucture.dto.SaveProjectDataDTO;

import java.net.URI;

import static pmanager.infrastucture.controller.RestConstants.PATH_PROJECTS;


@RestController
//http://localhost:8080/projects
@RequestMapping(PATH_PROJECTS)
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class ProjectRestResource {
    private final ProjectService projectService;

    @PostMapping
    //Mapeia requisições HTTP POST para o endpoint "/projects"
    public ResponseEntity<ProjectDTO> createProject(@RequestBody @Valid SaveProjectDataDTO saveProjectData){
        Project project = projectService.createProject(saveProjectData);

        return ResponseEntity
                //retorna 201 (valido), retorna o corpo
                .created(URI.create("/projects" + project.getId()))
                .body(ProjectDTO.create(project));
    }
    //...projects/{id} (Acesso ao projeto criado)
    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> loadProject(@PathVariable("id") String projectId){
        Project project = projectService.loadProject(projectId);
        return ResponseEntity.ok(ProjectDTO.create(project));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable("id") String projectId){
        projectService.deleteProject(projectId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> updateProject(
        @PathVariable("id") String projectId,
        @RequestBody @Valid SaveProjectDataDTO saveProjectDataDTO
    ){
        Project project = projectService.updateProject(projectId, saveProjectDataDTO);
        return ResponseEntity.ok(ProjectDTO.create(project));
    }
}

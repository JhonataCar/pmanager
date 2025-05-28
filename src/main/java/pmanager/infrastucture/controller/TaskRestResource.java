package pmanager.infrastucture.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pmanager.domain.applicationservice.TaskService;
import pmanager.domain.entity.Task;
import pmanager.infrastucture.dto.SaveMemberDataDTO;
import pmanager.infrastucture.dto.SaveTaskDataDTO;
import pmanager.infrastucture.dto.TaskDTO;
import pmanager.infrastucture.util.SortProperties;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static pmanager.infrastucture.controller.RestConstants.PATH_TASKS;


@RestController
//http://localhost:8080/projects
@RequestMapping(PATH_TASKS)
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class TaskRestResource {
    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody @Valid SaveTaskDataDTO saveTaskData){
        Task task = taskService.createTask(saveTaskData);

        return ResponseEntity
                .created(URI.create(PATH_TASKS + "/" + task.getId()))
                .body(TaskDTO.create(task));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> loadTask(@PathVariable("id") String taskId){
        Task task = taskService.loadTask(taskId);
        return ResponseEntity.ok(TaskDTO.create(task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") String taskId){
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(
            @PathVariable("id") String taskId,
            @RequestBody @Valid SaveTaskDataDTO saveTaskData)
    {
        Task task = taskService.updateTask(taskId, saveTaskData);
        return ResponseEntity.ok(TaskDTO.create(task));
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> findTasks(
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam(value = "memberId", required = false) String memberId,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "partialTitle", required = false) String partialTitle,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "direction", required = false) String direction,
            @RequestParam(value = "sort", required = false) SortProperties properties
            ){
        Page<Task> tasks = taskService.findTasks(
                projectId,
                memberId,
                status,
                partialTitle,
                page,
                direction,
                Optional
                        .ofNullable(properties)
                        .map(SortProperties::getSortPropertiesList)
                        .orElse(List.of())
        );

        return ResponseEntity.ok(tasks.stream().map(TaskDTO::create).toList());
    }
}

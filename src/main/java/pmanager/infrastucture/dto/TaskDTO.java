package pmanager.infrastucture.dto;

import lombok.Data;
import pmanager.domain.entity.Member;
import pmanager.domain.entity.Task;
import pmanager.domain.model.TaskStatus;

import java.util.Optional;

@Data
public class TaskDTO {
    private final String id;
    private final String title;
    private final String description;
    private final Integer numberOfDays;
    private final TaskStatus status;
    private final ProjectDTO project;
    private final MemberDTO member;

    public static TaskDTO create(Task task){
        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getNumberOfDay(),
                task.getStatus(),
                Optional.ofNullable(task.getProject()).map(ProjectDTO::create).orElse(null),
                Optional.ofNullable(task.getAssignedMember()).map(MemberDTO::create).orElse(null)
        );
    }
}

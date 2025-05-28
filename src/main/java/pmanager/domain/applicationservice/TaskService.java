package pmanager.domain.applicationservice;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pmanager.domain.entity.Member;
import pmanager.domain.entity.Project;
import pmanager.domain.entity.Task;
import pmanager.domain.exceptionDomain.InvalidTaskStatusException;
import pmanager.domain.exceptionDomain.TaskNoFoundException;
import pmanager.domain.model.TaskStatus;
import pmanager.domain.repository.TaskRepository;
import pmanager.infrastucture.config.AppConfigProperties;
import pmanager.infrastucture.dto.SaveTaskDataDTO;
import pmanager.infrastucture.util.PaginationHelper;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    /**
     * Serviço responsável por gerenciar tarefas/task's do sistema.
     *
     *Principais funções:
     *-Criação, leitura, atualização e exclusão de tarefas.
     *-Busca de tarefas com suporte para paginação e filtros (por projeto, membro, status ou título parcial).
     *-Conversão de strings para o tipo enumerado TaskStatus.
     *-Validação e busca de projetos e membros associados.
     */

    private final ProjectService projectService;
    private final MemberService memberService;
    private final TaskRepository taskRepository;
    private final AppConfigProperties props;

    @Transactional
    public Task createTask(SaveTaskDataDTO saveTaskData){
        Project project = getProjectIfPossible(saveTaskData.getProjectId());

        Member member = getMemberIfPossible(saveTaskData.getMemberId());

        Task task = Task
                .builder()
                .title(saveTaskData.getTitle())
                .description(saveTaskData.getDescription())
                .numberOfDay(saveTaskData.getNumberOfDays())
                .status(TaskStatus.PENDING)
                .project(project)
                .assignedMember(member)
                .build();

        taskRepository.save(task);
        return task;
    }

    public Task loadTask(String taskId){
        return taskRepository
                .findById(taskId)
                .orElseThrow(() -> new TaskNoFoundException(taskId));
    }

    @Transactional
    public void deleteTask(String taskId){
        Task task = loadTask(taskId);
        taskRepository.delete(task);
    }

    @Transactional
    public Task updateTask(String taskId, SaveTaskDataDTO saveTaskData){
        Project project = getProjectIfPossible(saveTaskData.getProjectId());

        Member member = getMemberIfPossible(saveTaskData.getMemberId());

        Task task = loadTask(taskId);

        task.setTitle(saveTaskData.getTitle());
        task.setDescription(saveTaskData.getDescription());
        task.setNumberOfDay(saveTaskData.getNumberOfDays());
        task.setStatus(convertToTaskStatus(saveTaskData.getStatus()));
        task.setProject(project);
        task.setAssignedMember(member);

        return task;
    }

    public Page<Task> findTasks(
            String projectId,
            String memberId,
            String statusStr,
            String partialTitle,
            Integer page,
            String directionStr,
            List<String> properties
    ){
        return taskRepository.find(
                projectId,
                memberId,
                Optional.ofNullable(statusStr).map(this::convertToTaskStatus).orElse(null),
                partialTitle,
                PaginationHelper.createPageable(page, props.getGeneral().getPageSize(), directionStr, properties)
        );
    }

    public TaskStatus convertToTaskStatus(String statusStr){
        try{
            return TaskStatus.valueOf(statusStr);
        } catch (IllegalArgumentException | NullPointerException e)
        {
            throw new InvalidTaskStatusException(statusStr);
        }

    }

    private Member getMemberIfPossible(String memberId) {
        Member member =  null;
        if (!Objects.isNull(memberId)){
            member = memberService.loadMemberById(memberId);
        }
        return member;
    }

    private Project getProjectIfPossible(String projectId) {
        Project project =  null;
        if (!Objects.isNull(projectId)){
            project = projectService.loadProject(projectId);
        }
        return project;
    }
}

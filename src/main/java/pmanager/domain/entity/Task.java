package pmanager.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pmanager.domain.model.TaskStatus;

import static jakarta.persistence.GenerationType.UUID;

/*
Entity; Entidade que representa uma tarefa
Data: get e set
 */
@Entity
@Data
//Construtor e Builder
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = UUID)
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @Column(name = "title", nullable = false, length = 80)
    private String title;

    @Column(name = "description", nullable = false, length = 150)
    private String description;

    @Column(name = "number_of_day", nullable = false)
    private Integer numberOfDay;

    //Status atual da tarefa
    @Column(name = "Status", nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    //Relação muitos-para-um com a entidade Project.
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    //Relação muitos-para-um com a entidade Membe
    @ManyToOne
    @JoinColumn(name = "assigned_member")
    private Member assignedMember;
}

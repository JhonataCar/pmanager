package pmanager.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import pmanager.domain.model.ProjectStatus;

import java.time.LocalDate;
import java.util.List;

/*JPA; É uma especificação da linguagem Java para mapear objetos
Java para tabelas de banco de dados*/
@Entity
@Table(name = "project")

/*Otimização (Visual) do codigo Getter e Setter (Data),
contrutor parametrizado @AllArgsConstructor.*/
@Data
@Builder //criar objetos complexos de forma mais legível,
@AllArgsConstructor
@NoArgsConstructor

public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    //coluna e especificações.
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @Column(name = "name", nullable = false, length = 80)
    private String name;

    @Column(name = "description", nullable = false, length = 150)
    private String description;

    @Column(name = "initialDate", nullable = false)
    private LocalDate initialDate;

    @Column(name = "finalData", nullable = false)
    private LocalDate finalData;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    //Relação muitos-para-muitos com membros.
    @ManyToMany
    @JoinTable(
            name = "project_members",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id")
            )
    private List<Member>members;

    //Relação um-para-muitos com tarefas.
    @OneToMany(mappedBy = "project", orphanRemoval = true)
    private List<Task> tasks;
}


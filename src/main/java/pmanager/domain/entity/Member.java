package pmanager.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static jakarta.persistence.GenerationType.UUID;

@Entity
@Table(name = "member")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = UUID)
    //id
    @Column(name = "id", nullable = false, length = 36)
    private String id;
    //coluna e especificações.
    @Column(name = "secret", nullable = false, length = 36)
    private String secret;

    @Column(name = "name", nullable = false, length = 80)
    private String name;

    @Column(name = "email", nullable = false, length = 59)
    private String email;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted;

    //Relação muitos-pra-muitos com a entidade Project.
    @ManyToMany(mappedBy = "members")
    private List<Project> projects;
}

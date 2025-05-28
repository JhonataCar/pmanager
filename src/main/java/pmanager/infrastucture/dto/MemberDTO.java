package pmanager.infrastucture.dto;

import lombok.Data;
import pmanager.domain.entity.Member;
import pmanager.domain.entity.Project;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;


@Data
public class MemberDTO {
    private final String id;
    private final String secret;
    private final String name;
    private final String email;
    private final Set<String> projectIds;

    public static MemberDTO create(Member member){
        return new MemberDTO(
                member.getId(),
                member.getSecret(),
                member.getName(),
                member.getEmail(),
                /*cria uma lista vazia, caso getProjects for nul
                retorna uma coluna id
                 */
                Optional
                        .ofNullable(member.getProjects())
                        .orElse(List.of())
                        .stream()
                        .map(Project::getId)
                        .collect(toSet())
        );
    }
}

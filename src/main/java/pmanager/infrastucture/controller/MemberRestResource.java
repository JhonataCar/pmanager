package pmanager.infrastucture.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pmanager.domain.applicationservice.MemberService;
import pmanager.domain.entity.Member;
import pmanager.domain.entity.Project;
import pmanager.infrastucture.dto.MemberDTO;
import pmanager.infrastucture.dto.ProjectDTO;
import pmanager.infrastucture.dto.SaveMemberDataDTO;
import pmanager.infrastucture.dto.SaveProjectDataDTO;

import java.net.URI;
import java.util.List;

import static pmanager.infrastucture.controller.RestConstants.PATH_MEMBERS;
import static pmanager.infrastucture.controller.RestConstants.PATH_PROJECTS;

/*
O **ponto de entrada** das requisições HTTP sobre projetos.
Essa classe é responsável por expor os endpoints REST relacionados
- /projects POST
- /projects/{id} GET
- /projects/{id} PUT
- /projects/{id} DELETE
 */

@RestController
//http://localhost:8080/projects
@RequestMapping(PATH_MEMBERS)
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class MemberRestResource {
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberDTO> createMember(@RequestBody @Valid SaveMemberDataDTO saveMemberData){
        Member member = memberService.createMember(saveMemberData);

        return ResponseEntity
                .created(URI.create(PATH_MEMBERS + "/" + member.getId()))
                .body(MemberDTO.create(member));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberDTO> loadMemberById(@PathVariable("id") String memberId){
        Member member = memberService.loadMemberById(memberId);
        return ResponseEntity.ok(MemberDTO.create(member));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable("id") String memberId){
        memberService.deleteMember(memberId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberDTO> UpdateMember(
            @PathVariable("id") String memberId,
            @Valid @RequestBody SaveMemberDataDTO saveMemberData
    ){
        Member member = memberService.updateMember(memberId, saveMemberData);
        return ResponseEntity.ok(MemberDTO.create(member));
    }

    // -> GET .../members
    // -> GET .../members?email={email}
    @GetMapping
    public ResponseEntity<List<MemberDTO>> findMembers(
            @RequestParam(value = "email", required = false) String email
    ){
        List<Member> members = memberService.findMembers(email);
        return ResponseEntity.ok(
                members.stream().map(MemberDTO::create).toList()
        );
    }
}

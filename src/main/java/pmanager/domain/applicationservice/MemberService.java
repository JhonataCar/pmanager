package pmanager.domain.applicationservice;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pmanager.domain.entity.Member;
import pmanager.domain.exceptionDomain.DuplicateMemberException;
import pmanager.domain.exceptionDomain.MemberNoFoundException;
import pmanager.domain.repository.MemberRepository;
import pmanager.infrastucture.dto.SaveMemberDataDTO;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    /**
     *Serviço responsável por gerenciar membros do sistema.
     *
     *Principais operações:
     *-Criação de novos membros.
     *-Busca por ID.
     *-Exclusão logica de membros.
     *-Atualização de membros existentes.
     *-Validaçao se ja existem membros com o mesmo email.
     */

    public Member createMember(SaveMemberDataDTO saveMemberData){
        if(existsMemberWithEmail(saveMemberData.getEmail(), null)){
            throw new DuplicateMemberException(saveMemberData.getEmail());
        }

        Member member = Member
                .builder()
                .name(saveMemberData.getName())
                .email(saveMemberData.getEmail())
                .secret(UUID.randomUUID().toString())
                .deleted(false)
                .build();

        memberRepository.save(member);
        return member;
    }

    @Transactional
    public Member loadMemberById(String memberId){
        return memberRepository
                .findByIdAndDeleted(memberId, false)
                .orElseThrow(()-> new MemberNoFoundException(memberId));
    }

    @Transactional
    public void deleteMember(String memberId){
        Member member = loadMemberById(memberId);
        member.setDeleted(true);
    }

    @Transactional
    public Member updateMember(String memberId, SaveMemberDataDTO saveMemberData){
        if(existsMemberWithEmail(saveMemberData.getEmail(), memberId)){
            throw new DuplicateMemberException(saveMemberData.getEmail());
        }

        Member member = loadMemberById(memberId);

        member.setName(saveMemberData.getName());
        member.setEmail(saveMemberData.getEmail());

        return member;
    }

    private boolean existsMemberWithEmail(String email, String idToExclude){
       return memberRepository
               .findByEmailAndDeleted(email, false)
               .filter(m -> !Objects.equals(m.getId(), idToExclude))
               .isPresent();
    }

    public List<Member> findMembers(String email) {
        List<Member> members;

        if (Objects.isNull(email)){
            members = memberRepository.findAllNotDeleted2();
        } else{
            members = memberRepository
                    .findByEmailAndDeleted(email, false)
                    .map(List::of)
                    .orElse(List.of());
        }

        return members;
    }
}

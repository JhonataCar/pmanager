package pmanager.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pmanager.domain.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
    Optional<Member> findByIdAndDeleted(String id, boolean deleted);

    Optional<Member> findByEmailAndDeleted(String email, boolean deleted);

    default List<Member> findAllNotDeleted(){
        return findAll()
                .stream()
                .filter(m -> !m.getDeleted())
                .toList();
    }

    @Query("""
            SELECT m FROM Member m WHERE m.deleted = false ORDER BY m.name
            """)
    List<Member> findAllNotDeleted2();
}

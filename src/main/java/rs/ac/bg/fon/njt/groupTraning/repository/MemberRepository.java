package rs.ac.bg.fon.njt.groupTraning.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.njt.groupTraning.model.Member;


public interface MemberRepository extends JpaRepository<Member, Long>{
     Member findByName(String name);
    Optional<Member> findByJmbg(String jmbg);
    List<Member> findByGroupId(long groupId);
}

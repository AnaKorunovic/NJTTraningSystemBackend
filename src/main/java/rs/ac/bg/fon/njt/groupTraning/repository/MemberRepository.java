package rs.ac.bg.fon.njt.groupTraning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.njt.groupTraning.model.Member;


public interface MemberRepository extends JpaRepository<Member, Long>{
    
}

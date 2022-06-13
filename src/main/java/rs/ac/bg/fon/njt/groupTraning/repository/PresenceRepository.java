package rs.ac.bg.fon.njt.groupTraning.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.njt.groupTraning.model.Presence;


public interface  PresenceRepository extends JpaRepository<Presence, Long>{
   List<Presence> findByAppointment(long appId);
    Optional<List<Presence>> findByMember(long memberId);
    Optional<Presence> findByMemberAndAppointment(long memberId,Long appId);

    public void deleteByMemberAndAppointment(Long memId, Long appId);

  
    
}

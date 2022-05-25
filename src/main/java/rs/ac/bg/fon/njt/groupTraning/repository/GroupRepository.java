package rs.ac.bg.fon.njt.groupTraning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.njt.groupTraning.model.Group;


public interface GroupRepository extends JpaRepository<Group, Long>  {
    
}

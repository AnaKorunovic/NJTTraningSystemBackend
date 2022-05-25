package rs.ac.bg.fon.njt.groupTraning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.njt.groupTraning.model.Trainer;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    
    
}

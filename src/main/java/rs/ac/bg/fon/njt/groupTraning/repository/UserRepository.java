package rs.ac.bg.fon.njt.groupTraning.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.njt.groupTraning.model.User;



public interface UserRepository extends JpaRepository<User, Long>{
    
    public Optional<User> findUserByUsername(String username);
    public Optional<User> findUserByUsernameAndPassword(String username,String password);
    
}

package rs.ac.bg.fon.njt.groupTraning.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.njt.groupTraning.repository.UserRepository;
import rs.ac.bg.fon.njt.groupTraning.model.User;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User loadUserByUsername(String username) throws Exception {
        Optional<User> optionalUser = userRepository.findUserByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new Exception("User with username = " + username + " does not exist.");
        }
        return optionalUser.get();
    }
    
    public User loadUserByUsernameAndPassword(String username,String password) throws Exception  {
        Optional<User> optionalUser = userRepository.findUserByUsernameAndPassword(username,password);
        if (optionalUser.isEmpty()) {
            throw new Exception("User with username = " + username + " and password does not exist.");
        }
        return optionalUser.get();
    }
    

    public User loadUserById(Long id) throws Exception {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new Exception("User with id = " + id + " does not exist.");
        }
        return optionalUser.get();
    }

    public User addUser(User user) throws Exception {
        if (user == null) {
            throw new Exception("Could not save user. User is null.");
        }
        Optional<User> userExist = userRepository.findUserByUsername(user.getUsername());
        if (userExist.isPresent()) {
            throw new Exception("There is an user with this username:" + user.getUsername());
        }
        User newUser = new User(0L,
                user.getName(),
                user.getLastname(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail());

        return userRepository.save(user);
    }
}

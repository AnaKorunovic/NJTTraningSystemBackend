package rs.ac.bg.fon.njt.groupTraning.service;

import java.util.List;
import java.util.Optional;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.njt.groupTraning.repository.UserRepository;
import rs.ac.bg.fon.njt.groupTraning.model.User;

@Service
public class UserService {

    private final UserRepository userRepository;
     @Autowired
    private JavaMailSender mailSender;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User loadUserByUsername(String username) throws Exception {
        Optional<User> optionalUser = userRepository.findUserByUsername(username);
        if (!optionalUser.isPresent()) {
            return null;
        }
        return optionalUser.get();
    }

    public User loadUserByUsernameAndPassword(String username, String password) throws Exception {
        Optional<User> optionalUser = userRepository.findUserByUsernameAndPassword(username, password);
        if (!optionalUser.isPresent()) {
            throw new Exception("User with username = " + username + " and password does not exist.");
        }
        return optionalUser.get();
    }

    public User loadUserById(Long id) throws Exception {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
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
        User newUser = new User(
                user.getName(),
                user.getLastname(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail());

        return userRepository.save(user);
    }

    public List<User> loadUsers() {
        return userRepository.findAll();
    }

    public void sendEmail(String toEmail,String subject, String body ) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("korunovicana9@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        mailSender.send(message);
        System.out.println("Mail Send...");

    }



}

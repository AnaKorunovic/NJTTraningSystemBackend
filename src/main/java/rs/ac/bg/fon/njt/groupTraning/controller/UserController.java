package rs.ac.bg.fon.njt.groupTraning.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.njt.groupTraning.model.User;
import rs.ac.bg.fon.njt.groupTraning.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class UserController {

    @Autowired
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public List<User> getUsers() {
        return this.userService.loadUsers();
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User userForLogin) {

        //provera da li postoji user sa prosledjenim podacima za username i password
        try {
            User user = userService.loadUserByUsername(userForLogin.getUsername());
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if (user != null && encoder.matches(userForLogin.getPassword(), user.getPassword())) {
                System.out.println("User " + user.getUsername() + "logged.");
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
        } catch (Exception e) {
            //user ne postoji -> baca se izuzetak
            System.out.println(e.getMessage());
            throw new IllegalStateException("Bad credentials for login");

        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PostMapping()
    public ResponseEntity<?> addUser(@RequestBody User u) {

        try {
            User user = userService.loadUserByUsername(u.getUsername());
            if (user == null) {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                String encodedPassword = encoder.encode(u.getPassword());

                u.setPassword(encodedPassword);
                user = userService.addUser(u);
                System.out.println("User " + u.getUsername() + "added.");
                this.userService.sendEmail(user.getEmail(),"Traning System dobrodoslica", "Dobrodosao "+ user.getName()+" "+user.getLastname()+". Vas Traning System.");
                return ResponseEntity.ok(user);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    
   

}

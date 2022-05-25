package rs.ac.bg.fon.njt.groupTraning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.njt.groupTraning.model.User;
import rs.ac.bg.fon.njt.groupTraning.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public User login(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {

        //provera da li postoji user sa prosledjenim podacima za username i password
        try {
            User user = userService.loadUserByUsernameAndPassword(username, password);
            System.out.println("User " + user.getUsername() + "logged.");
            return user;
        } catch (Exception e) {
        //user ne postoji -> baca se izuzetak
            System.out.println(e.getMessage());
            throw new IllegalStateException("Bad credentials for login");

        }

    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody User u) {
        User user = null;
        try {
            user = userService.addUser(u);
            System.out.println("User " + u.getUsername() + "added.");
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).body(e.getMessage());
        }

    }

}

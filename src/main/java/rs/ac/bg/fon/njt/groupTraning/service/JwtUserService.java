package rs.ac.bg.fon.njt.groupTraning.service;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

@Service
public class JwtUserService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            rs.ac.bg.fon.njt.groupTraning.model.User user = userService.loadUserByUsername(username);

            if (user.getUsername().equals(username)) {
                return new User(user.getUsername(), user.getPassword(),
                        new ArrayList<>());
            } 
        } catch (Exception ex) {
            Logger.getLogger(JwtUserService.class.getName()).log(Level.SEVERE, null, ex);
        }
                throw new UsernameNotFoundException("User not found with username: " + username);
    }
}


package pl.webapp.arbitratus.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.webapp.arbitratus.Entity.User;
import pl.webapp.arbitratus.Repository.UserRepository;

@RestController
@CrossOrigin
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/registration")
    public User registerUser(User user) {
        if (!userRepository.existsUserByUsername(user.getUsername())) {
            user.setUsername(user.getUsername().toLowerCase());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
        else
        {
            System.out.println("Taki koleżka już istnieje, wybierz inną nazwę");
            return null;}
    }


}

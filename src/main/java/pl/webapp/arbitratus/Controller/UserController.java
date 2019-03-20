package pl.webapp.arbitratus.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.webapp.arbitratus.Entity.Shoppinglist;
import pl.webapp.arbitratus.Entity.User;
import pl.webapp.arbitratus.Repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //ZAREJESTRUJ NOWEGO UŻYTKOWNIKA
    @PostMapping("/registration")
    public User registerUser(@RequestBody User user) {
        if (userRepository.existsUserByUsername(user.getUsername())) {
            System.out.println("Taki koleżka już istnieje, wybierz inną nazwę");
            return null;
        }
        else {
            user.setUsername(user.getUsername().toLowerCase());
            List<Shoppinglist> newList = new ArrayList<>();
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
    }


}

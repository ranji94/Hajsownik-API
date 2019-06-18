package pl.webapp.arbitratus.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.webapp.arbitratus.Access.ApiResponse;
import pl.webapp.arbitratus.Access.JwtAuthenticationResponse;
import pl.webapp.arbitratus.Access.LoginRequest;
import pl.webapp.arbitratus.Access.SignUpRequest;
import pl.webapp.arbitratus.Entity.Role;
import pl.webapp.arbitratus.Entity.User;
import pl.webapp.arbitratus.Enum.RoleName;
import pl.webapp.arbitratus.Exception.AppException;
import pl.webapp.arbitratus.Repository.RoleRepository;
import pl.webapp.arbitratus.Repository.UserRepository;
import pl.webapp.arbitratus.Security.JwtTokenProvider;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin
public class AuthenticationController {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtTokenProvider tokenProvider;

    //logowanie
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest)
    {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    //pobierz wszystkich userów
    @GetMapping("/users/getall")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/users/current")
    public String getCurrentUser(Principal principal){
        try{
            return principal.getName();} catch (NullPointerException e){
            logger.error("Brak zalogowanego użytkownika");
            return null;
        }
    }

    //rejestracja
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsUserByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Nazwa użytkownika jest już zajęta!"),
                    HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(signUpRequest.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("Rola użytkownika nie została ustawiona."));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "Użytkownika zarejestrowano pomyślnie"));
    }
}

package pl.webapp.arbitratus.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.webapp.arbitratus.Entity.User;
import pl.webapp.arbitratus.Repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository)
    {
        super();
        this.userRepository=userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username);
        if(null==user)
        {
            throw new UsernameNotFoundException("Nie można znaleźć użytkownika "+username);
        }
        return new CustomUserPrincipal(user);
    }
}

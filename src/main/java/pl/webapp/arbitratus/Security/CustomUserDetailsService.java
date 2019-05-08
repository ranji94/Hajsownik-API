package pl.webapp.arbitratus.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.webapp.arbitratus.Entity.User;
import pl.webapp.arbitratus.Repository.UserRepository;

import javax.transaction.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findAllByUsername(username).orElseThrow(()->
                new UsernameNotFoundException("Użytkownik o tej nazwie nie został znaleziony: "+username));
    return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("Użytkownik nie znaleziony o podanym id : " + id)
        );

        return UserPrincipal.create(user);
    }
}

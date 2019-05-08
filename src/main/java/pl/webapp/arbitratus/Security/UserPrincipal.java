package pl.webapp.arbitratus.Security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserPrincipal implements UserDetails {
    private Long id;
    private String name;
    private String username;
    private Collection<? extends GrantedAuthority> authorities;
}

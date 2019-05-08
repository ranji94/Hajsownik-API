package pl.webapp.arbitratus.Access;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public String getUsername()
    {
        
    }
}

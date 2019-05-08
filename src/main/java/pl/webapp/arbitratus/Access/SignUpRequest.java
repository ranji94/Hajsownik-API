package pl.webapp.arbitratus.Access;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignUpRequest {
    @NotBlank
    @Size(min=4, max=40)
    private String username;

    
}

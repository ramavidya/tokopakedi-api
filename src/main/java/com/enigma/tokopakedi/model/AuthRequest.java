package com.enigma.tokopakedi.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequest {
    @Email(message = "invalid email")
    private String email;
    @Size(min = 6, message = "password minimum 6 character")
    private String password;
}

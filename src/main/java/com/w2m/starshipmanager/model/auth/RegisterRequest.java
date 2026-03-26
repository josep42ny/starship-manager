package com.w2m.starshipmanager.model.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class RegisterRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @Setter(AccessLevel.NONE)
    private String role = "USER";

}

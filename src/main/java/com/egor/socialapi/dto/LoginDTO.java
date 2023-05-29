package com.egor.socialapi.dto;

import com.egor.socialapi.validation.Password;
import com.egor.socialapi.validation.ValidEmail;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    @NotNull
    @ValidEmail
    private String email;

    @NotNull
    @Password
    private String password;
}

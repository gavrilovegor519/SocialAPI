package com.egor.socialapi.dto;

import com.egor.socialapi.validation.FieldMatch;
import com.egor.socialapi.validation.Password;
import com.egor.socialapi.validation.ValidEmail;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@FieldMatch.List({
        @FieldMatch(first = "password", second = "passwordConfirmation", message = "Password fields must match")
})
public class UserDTO {

    private Long id;

    @NotNull
    @ValidEmail
    private String email;

    @NotNull
    @Size(min = 6, max = 100, message = "Min size is 6 and max size is 100")
    @Password
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotNull
    @Size(min = 6, max = 100, message = "Min size is 6 and max size is 100")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String passwordConfirmation;

    @NotNull
    @Size(min = 2, max = 100, message = "Min size is 2 and max size is 100")
    private String username;

}

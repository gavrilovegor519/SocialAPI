package com.egor.socialapi.dto;

import com.egor.socialapi.validation.*;
import lombok.*;

import jakarta.validation.constraints.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldMatch.List({
        @FieldMatch(first = "password", second = "passwordConfirmation", message = "Password fields must match")
})
public class UserDTO {

    private Long id;

    @NotNull
    @ValidEmail
    private String email;

    @NotNull
    @Size(min=6, max=100, message = "Min size is 6 and max size is 100")
    @Password
    private String password;

    @NotNull
    @Size(min=6, max=100, message = "Min size is 6 and max size is 100")
    private String passwordConfirmation;

    @NotNull
    @Size(min=2, max=100, message = "Min size is 2 and max size is 100")
    private String username;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO user = (UserDTO) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

package com.egor.socialapi.dto;

import com.egor.socialapi.entities.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostsDTO {
    private long id;
    private User loggedInUser;
    @NotNull
    @Size(min = 3, max = 10000)
    private String content;
    private String imageUrl;
    private LocalDateTime time;
}

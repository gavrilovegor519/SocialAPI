package com.egor.socialapi.dto;

import lombok.*;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {

    private LocalDateTime time;

    @NotNull
    @Size(min=3, max = 3000)
    private String message;
    private UserDTO sender;
    private UserDTO receiver;
    private Long companionId;
}

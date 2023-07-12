package com.egor.socialapi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "posts")
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "loggedInUser")
    private User loggedInUser;

    @Column(name = "content")
    private String content;

    @Column(name = "imageUrl")
    private String imageUrl;

    @Column(name = "time")
    private LocalDateTime time;
}

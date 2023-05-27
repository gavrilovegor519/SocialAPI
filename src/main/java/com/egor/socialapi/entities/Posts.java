package com.egor.socialapi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts")
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "loggedInUser")
    private User loggedInUser;

    @ManyToOne
    @JoinColumn(name = "timelineUser")
    private User timelineUser;

    @Column(name = "content")
    private String content;

    @Column(name = "imageUrl")
    private String imageUrl;

    @Column(name = "time")
    private LocalDateTime time;
}

package org.example.newsfeedteamproject.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "follow")
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "to_user")
    private User toUserId;

    @ManyToOne
    @JoinColumn(name = "from_user")
    private User fromUserId;

    public Follow(User toUser, User fromUSer) {
        this.toUserId = toUserId;
        this.fromUserId = fromUserId;
    }
}

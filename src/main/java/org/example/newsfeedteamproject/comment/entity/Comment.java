package org.example.newsfeedteamproject.comment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.newsfeedteamproject.base_entity.BaseEntity;
import org.example.newsfeedteamproject.post.entity.Post;
import org.example.newsfeedteamproject.user.entity.User;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Comment(String contents, Post post, User user) {
        this.post = post;
        this.user = user;
        this.contents = contents;
    }

    public void update(String contents) {
        this.contents = contents;
    }
}
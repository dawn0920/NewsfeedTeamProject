package org.example.newsfeedteamproject.comment.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.newsfeedteamproject.entity.BaseEntity;
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
    @NotBlank(message = "내용은 필수 입력값입니다.")
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Comment(Post post, User user, String contents) {
        this.post = post;
        this.user = user;
        this.contents = contents;
    }

    public void update(String contents) {
        this.contents = contents;
    }
}
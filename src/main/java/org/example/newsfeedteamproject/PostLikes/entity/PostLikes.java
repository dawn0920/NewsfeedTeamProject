package org.example.newsfeedteamproject.PostLikes.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.newsfeedteamproject.post.entity.Post;
import org.example.newsfeedteamproject.user.entity.User;

@Getter
@Entity
@NoArgsConstructor
// 유저 id당 게시물에 하나의 좋아요밖에 누르지 못하게 설정
@Table(name = "post_likes",
    uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "post_id"}))
public class PostLikes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 좋아요를 누른 사용자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User fromUser;

    // 좋아요를 받은 게시글
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post toPost   ;

    public PostLikes(User fromUser, Post toPost) {
        this.fromUser = fromUser;
        this.toPost = toPost;
    }
}

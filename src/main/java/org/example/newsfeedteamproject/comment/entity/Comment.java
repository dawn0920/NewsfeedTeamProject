package org.example.newsfeedteamproject.comment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.newsfeedteamproject.PostLikes.entity.PostLikes;
import org.example.newsfeedteamproject.base_entity.BaseEntity;
import org.example.newsfeedteamproject.comment.dto.CommentRequestDto;
import org.example.newsfeedteamproject.commentLikes.entity.CommentLikes;
import org.example.newsfeedteamproject.post.entity.Post;
import org.example.newsfeedteamproject.user.entity.User;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int commentLike =0;

    @Column(nullable = false)
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 좋아요 연관관계
     */
    @OneToMany(mappedBy = "toComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<CommentLikes> commentLikes = new ArrayList<>();

    public Comment(Post post, User user, String contents) {
        this.post = post;
        this.user = user;
        this.contents = contents;
    }

    public void update(CommentRequestDto dto) {
        this.contents = dto.getContents();
    }

    public void increaseLike() {this.commentLike++;}

    public void decreaseLike(){
        this.commentLike = Math.max(0,this.commentLike - 1);
    }
}
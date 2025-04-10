package org.example.newsfeedteamproject.commentLikes.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.newsfeedteamproject.comment.entity.Comment;
import org.example.newsfeedteamproject.user.entity.User;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "comment_likes",
uniqueConstraints = @UniqueConstraint(columnNames = {"user_id","comment_id"}))
public class CommentLikes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User fromUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment toComment;

    public CommentLikes(User fromUser, Comment toComment) {
        this.fromUser = fromUser;
        this.toComment = toComment;
    }
}

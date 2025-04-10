package org.example.newsfeedteamproject.commentLikes.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.newsfeedteamproject.comment.entity.Comment;
import org.example.newsfeedteamproject.user.entity.User;

/**
 * 댓글 좋아요 정보를 저장하는 엔티티 클래스입니다.
 * 사용자와 댓글 간의 좋아요 관계를 나타냅니다.
 */
@Getter
@Entity
@NoArgsConstructor
@Table(name = "comment_likes",
uniqueConstraints = @UniqueConstraint(columnNames = {"user_id","comment_id"}))
public class CommentLikes {

    /**
     * 댓글 좋아요의 고유 식별자입니다.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 좋아요를 누른 사용자 정보입니다.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User fromUser;

    /**
     * 좋아요를 받은 댓글 정보입니다.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment toComment;

    /**
     * 사용자와 댓글을 받아 댓글 좋아요 객체를 생성합니다.
     *
     * @param fromUser 좋아요를 누른 사용자
     * @param toComment 좋아요를 받은 댓글
     */
    public CommentLikes(User fromUser, Comment toComment) {
        this.fromUser = fromUser;
        this.toComment = toComment;
    }
}

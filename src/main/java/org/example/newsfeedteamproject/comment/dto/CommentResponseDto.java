package org.example.newsfeedteamproject.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.newsfeedteamproject.comment.entity.Comment;
import org.example.newsfeedteamproject.commentLikes.entity.CommentLikes;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor

public class CommentResponseDto {

    private final Long id;
    private final Long userId;
    private final Long postId;
    private final String contents;
    private final LocalDateTime creatTime;
    private final LocalDateTime modifiedTime;
    private final int commentLike;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.userId = comment.getUser().getId();
        this.postId = comment.getPost().getId();
        this.contents = comment.getContents();
        this.creatTime = comment.getCreatTime();
        this.modifiedTime = comment.getModifiedTime();
        this.commentLike = comment.getCommentLike();
    }
}
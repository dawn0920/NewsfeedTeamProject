package org.example.newsfeedteamproject.comment.dto;

import lombok.Getter;
import org.example.newsfeedteamproject.comment.entity.Comment;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private final Long id;
    private final Long userId;
    private final Long postId;
    private final String contents;
    private final LocalDateTime creatTime;
    private final LocalDateTime modifiedAt;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.userId = comment.getUser().getId();
        this.postId = comment.getPost().getId();
        this.contents = comment.getContents();
        this.creatTime = comment.getCreatTime();
        this.modifiedAt = comment.getModifiedTime();
    }
}
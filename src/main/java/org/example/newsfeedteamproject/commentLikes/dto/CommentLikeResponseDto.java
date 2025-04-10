package org.example.newsfeedteamproject.commentLikes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentLikeResponseDto {
    private boolean success;
    private int commentLikeCount;
}

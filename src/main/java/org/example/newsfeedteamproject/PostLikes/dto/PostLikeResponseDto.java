package org.example.newsfeedteamproject.PostLikes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor

public class PostLikeResponseDto {
    private boolean success;
    private int postLikeCount;

    public PostLikeResponseDto(boolean success, int postLikeCount) {
        this.success = success;
        this.postLikeCount = postLikeCount;
    }
}

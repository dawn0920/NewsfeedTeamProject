package org.example.newsfeedteamproject.PostLikes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostLikeResponseDto {
    private boolean success;
    private int postLikeCount;
}

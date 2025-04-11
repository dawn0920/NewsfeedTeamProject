package org.example.newsfeedteamproject.PostLikes.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor

public class PostCountLike {
    private Long countPostLike;

    public PostCountLike(Long countPostLike) {
        this.countPostLike = countPostLike;
    }
}

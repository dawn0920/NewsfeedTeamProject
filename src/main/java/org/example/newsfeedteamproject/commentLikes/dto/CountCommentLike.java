package org.example.newsfeedteamproject.commentLikes.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor

public class CountCommentLike {
    private Long countCommentLike;

    public CountCommentLike(Long countCommentLike) {
        this.countCommentLike = countCommentLike;
    }
}

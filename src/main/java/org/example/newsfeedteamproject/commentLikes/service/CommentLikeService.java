package org.example.newsfeedteamproject.commentLikes.service;


import org.example.newsfeedteamproject.commentLikes.dto.CommentLikeResponseDto;
import org.example.newsfeedteamproject.user.entity.User;

public interface CommentLikeService {
    CommentLikeResponseDto toggleCommentLike(User fromUser, Long commentId);
}

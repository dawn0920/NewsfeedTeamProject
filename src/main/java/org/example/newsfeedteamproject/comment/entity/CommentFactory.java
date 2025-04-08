package org.example.newsfeedteamproject.comment.entity;

import org.example.newsfeedteamproject.comment.dto.CommentRequestDto;
import org.example.newsfeedteamproject.post.entity.Post;
import org.example.newsfeedteamproject.user.entity.User;

public class CommentFactory {

    public static Comment saveComment(Post post, User user, CommentRequestDto requestDto) {
        return new Comment(post, user, requestDto.getContents());
    }
}

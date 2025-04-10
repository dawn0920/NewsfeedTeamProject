package org.example.newsfeedteamproject.commentLikes.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.newsfeedteamproject.PostLikes.service.PostLikeService;
import org.example.newsfeedteamproject.commentLikes.dto.CommentLikeResponseDto;
import org.example.newsfeedteamproject.commentLikes.service.CommentLikeService;
import org.example.newsfeedteamproject.global.consts.Const;
import org.example.newsfeedteamproject.user.entity.User;
import org.example.newsfeedteamproject.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;



@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor

public class CommentLikeController {
    private final CommentLikeService commentLikeService;
    private final UserRepository userRepository;
    private final PostLikeService postLikeService;

    @PostMapping("/{commentId}/like")
    public CommentLikeResponseDto toggleLike(
            @PathVariable Long commentId,
            @SessionAttribute(name = Const.LOGIN_USER) Long userId
    ){
        User fromUser = userRepository.findById(userId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"사용자를 찾을 수 없습니다."));
        return commentLikeService.toggleCommentLike(fromUser, commentId);
    }

}

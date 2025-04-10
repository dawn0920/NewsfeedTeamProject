package org.example.newsfeedteamproject.commentLikes.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.newsfeedteamproject.PostLikes.service.PostLikeService;
import org.example.newsfeedteamproject.commentLikes.dto.CommentLikeResponseDto;
import org.example.newsfeedteamproject.commentLikes.service.CommentLikeService;
import org.example.newsfeedteamproject.user.entity.User;
import org.example.newsfeedteamproject.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
            HttpSession session
    ){
        Long userId = (Long) session.getAttribute("LOGIN_USER");

        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인 후 시도해주세요");
        }
        User fromUser = userRepository.findById(userId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"사용자를 찾을 수 없습니다."));
        return commentLikeService.toggleCommentLike(fromUser, commentId);
    }

}

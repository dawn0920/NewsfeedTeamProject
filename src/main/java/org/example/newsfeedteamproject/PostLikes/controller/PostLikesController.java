package org.example.newsfeedteamproject.PostLikes.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.newsfeedteamproject.PostLikes.dto.PostLikeResponseDto;
import org.example.newsfeedteamproject.PostLikes.service.PostLikeService;
import org.example.newsfeedteamproject.post.service.PostService;
import org.example.newsfeedteamproject.user.entity.User;
import org.example.newsfeedteamproject.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostLikesController {

    private final PostLikeService postLikeService;
    private final PostService postService;
    private final UserService userService;

    @PostMapping("/{postId}/like") // 좋아요 토글
    public PostLikeResponseDto toggleLike(
            @PathVariable Long postId,
            HttpSession session
    ) {
        User fromUser = (User)session.getAttribute("user");

        if (fromUser == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인 후 시도해주세요.");
        }

        return postLikeService.toggleLike(fromUser, postId);
    }
}

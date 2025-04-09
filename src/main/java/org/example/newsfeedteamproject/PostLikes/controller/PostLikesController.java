package org.example.newsfeedteamproject.PostLikes.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.newsfeedteamproject.PostLikes.dto.PostLikeResponseDto;
import org.example.newsfeedteamproject.PostLikes.service.PostLikeService;
import org.example.newsfeedteamproject.user.entity.User;
import org.example.newsfeedteamproject.user.repository.UserRepository;
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
    private final UserRepository userRepository;

    @PostMapping("/{postId}/like") // 좋아요 토글
    public PostLikeResponseDto toggleLike(
            @PathVariable Long postId,
            HttpSession session
    ) {
        Long userId = (Long) session.getAttribute("LOGIN_USER");

        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인 후 시도해주세요.");
        }

        // userId로 사용자 조회
        User fromUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        return postLikeService.toggleLike(fromUser, postId);
    }
}

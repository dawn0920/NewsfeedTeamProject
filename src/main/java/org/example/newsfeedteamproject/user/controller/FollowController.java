package org.example.newsfeedteamproject.user.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.newsfeedteamproject.user.dto.FollowResponseDto;
import org.example.newsfeedteamproject.user.entity.User;
import org.example.newsfeedteamproject.user.repository.UserRepository;
import org.example.newsfeedteamproject.user.service.FollowService;
import org.example.newsfeedteamproject.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class FollowController {
    private final UserService userService;
    private final FollowService followService;
    private final UserRepository userRepository;


    @PostMapping("/{toUserId}/follow")
    public ResponseEntity<FollowResponseDto> toggleFollow(
            @PathVariable Long toUserId,
            HttpSession session
    ) {
        Long userId = (Long) session.getAttribute("LOGIN_USER");

        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인 후 시도해주세요.");
        }
        // userId로 User 객체 조회
        User fromUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        FollowResponseDto responseDto = followService.toggleFollowUser(fromUser, toUserId);
        return ResponseEntity.ok(responseDto);
    }
}

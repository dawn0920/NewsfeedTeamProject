package org.example.newsfeedteamproject.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.newsfeedteamproject.user.dto.followDto.FollowDto;
import org.example.newsfeedteamproject.user.dto.followDto.FollowResponseDto;
import org.example.newsfeedteamproject.user.entity.User;
import org.example.newsfeedteamproject.user.repository.UserRepository;
import org.example.newsfeedteamproject.user.service.FollowService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;
    private final UserRepository userRepository;

    // 팔로우 언팔로우
    @PostMapping("/{toUserId}/follow")
    public ResponseEntity<FollowResponseDto> toggleFollow(
            @PathVariable Long toUserId,
            @AuthenticationPrincipal(expression = "username") String email
    ) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Long userId = user.getId();
        // userId로 User 객체 조회
        User fromUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        FollowResponseDto responseDto = followService.toggleFollowUser(fromUser, toUserId);
        return ResponseEntity.ok(responseDto);
    }

    // 팔로잉 리스트 조회
    @GetMapping("{userId}/following")
    public ResponseEntity<List<FollowDto>> getFollowing(
            @PathVariable Long userId
    ) {
        List<FollowDto> followings = followService.getFollowings(userId);
        return ResponseEntity.ok(followings);
    }

    // 팔로우 리스트 조회
    @GetMapping("{userId}/follower")
    public ResponseEntity<List<FollowDto>> getFollower(
            @PathVariable Long userId
    ) {
        List<FollowDto> followers = followService.getFollower(userId);
        return ResponseEntity.ok(followers);
    }
}
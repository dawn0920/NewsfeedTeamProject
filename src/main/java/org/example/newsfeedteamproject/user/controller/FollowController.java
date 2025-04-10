package org.example.newsfeedteamproject.user.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.newsfeedteamproject.global.consts.Const;
import org.example.newsfeedteamproject.user.dto.FollowDto;
import org.example.newsfeedteamproject.user.dto.FollowListReponseDto;
import org.example.newsfeedteamproject.user.dto.FollowResponseDto;
import org.example.newsfeedteamproject.user.entity.User;
import org.example.newsfeedteamproject.user.repository.UserRepository;
import org.example.newsfeedteamproject.user.service.FollowService;
import org.example.newsfeedteamproject.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;
    private final UserRepository userRepository;


    @PostMapping("/{toUserId}/follow")
    public ResponseEntity<FollowResponseDto> toggleFollow(
            @PathVariable Long toUserId,
            @SessionAttribute(name = Const.LOGIN_USER) Long userId
    ) {
        // userId로 User 객체 조회
        User fromUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        FollowResponseDto responseDto = followService.toggleFollowUser(fromUser, toUserId);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("{userId}/following")
    public ResponseEntity<List<FollowDto>> getFollowing(
            @PathVariable Long userId
    ) {
        // userId로 User 객체 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        List<FollowDto> followings = followService.getFollowings(userId);
        return ResponseEntity.ok(followings);
    }

    @GetMapping("{userId}/follower")
    public ResponseEntity<List<FollowDto>> getFollower(
            @PathVariable Long userId
    ) {
        // userId로 User 객체 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        List<FollowDto> followers = followService.getFollower(userId);
        return ResponseEntity.ok(followers);
    }
}
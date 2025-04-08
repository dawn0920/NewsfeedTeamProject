package org.example.newsfeedteamproject.follow.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.newsfeedteamproject.follow.dto.FollowRequestDto;
import org.example.newsfeedteamproject.follow.dto.FollowResponseDto;
import org.example.newsfeedteamproject.follow.service.FollowService;
import org.example.newsfeedteamproject.user.entity.User;
import org.example.newsfeedteamproject.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;
    private final UserService userService;


    @PostMapping("/follow") // 팔로우
    public ResponseEntity<FollowResponseDto> followUser(
            @RequestBody FollowRequestDto requestDto,
            HttpSession session
    ) {
        User fromUser = (User)session.getAttribute("user");

        if (fromUser == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인 후 시도해주세요.");
        }

        FollowResponseDto responseDto = followService.follwoUser(fromUser, requestDto.getToUserId());
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/unfollow") // 언팔로우
    public  ResponseEntity<FollowResponseDto> unfollowUser(
            @RequestBody FollowRequestDto requestDto,
            HttpSession session
    ) {
        User fromUser = (User)session.getAttribute("user");

        if (fromUser == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인 후 시도해주세요.");
        }

        FollowResponseDto responseDto = followService.unfollwoUser(fromUser, requestDto.getToUserId());
        return ResponseEntity.ok(responseDto);
    }
}

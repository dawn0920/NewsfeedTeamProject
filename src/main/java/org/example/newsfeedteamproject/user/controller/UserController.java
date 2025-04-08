package org.example.newsfeedteamproject.user.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.newsfeedteamproject.user.dto.FollowRequestDto;
import org.example.newsfeedteamproject.user.dto.FollowResponseDto;
import org.example.newsfeedteamproject.user.dto.FollowRequestDto;
import org.example.newsfeedteamproject.user.dto.FollowResponseDto;
import org.example.newsfeedteamproject.user.dto.UserRequestDto;
import org.example.newsfeedteamproject.user.dto.UserResponseDto;
import org.example.newsfeedteamproject.user.entity.User;
import org.example.newsfeedteamproject.user.service.FollowService;
import org.example.newsfeedteamproject.user.entity.User;
import org.example.newsfeedteamproject.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final FollowService followService;

    /**
     * 회원가입 API
     * @param requestDto 요청 회원 정보
     * @return
     */

    @PostMapping("/signUp")
    public ResponseEntity<UserResponseDto> signUp(@RequestBody UserRequestDto requestDto){

        UserResponseDto userResponseDto = userService.signUp(requestDto);

        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }

    @PostMapping("/follow") // 팔로우
    public  ResponseEntity<FollowResponseDto> followUser(
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


    /**
     * 회원 선택 조회 API
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id){

        UserResponseDto userResponseDto = userService.findById(id);

        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    /**
     * 회원 전체 조회 API
     * @return
     */
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAll(){

        List<UserResponseDto> userResponseDtoList = userService.findAll();

        return new ResponseEntity<>(userResponseDtoList, HttpStatus.OK);
    }
}

package org.example.newsfeedteamproject.user.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.newsfeedteamproject.user.dto.UserRequestDto;
import org.example.newsfeedteamproject.user.dto.UserResponseDto;
import org.example.newsfeedteamproject.user.dto.*;
import org.example.newsfeedteamproject.user.entity.User;
import org.example.newsfeedteamproject.user.repository.FollowRepository;
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
public class UserController {

    private final UserService userService;
    private final FollowService followService;
    private final UserRepository userRepository;

    /**
     * 회원가입 API
     * @param requestDto 요청 회원 정보
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signUp(@RequestBody UserRequestDto requestDto){

        UserResponseDto userResponseDto = userService.signUp(requestDto);

        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
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

    /**
     * 회원 수정 API
     * @param id
     * @param requestDto 수정 데이터
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody UserRequestDto requestDto){

        userService.update(id, requestDto);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    /**
     * 회원 탈퇴 API
     * @param id
     * @param requestDto
     * @return
     */
    @PatchMapping("/{id}")
    public ResponseEntity<IsWithdrawnResponseDto> withdrawn(
            @PathVariable Long id,
            @RequestBody IsWithdrawnRequestDto requestDto){

        IsWithdrawnResponseDto isWithdrawnResponseDto = userService.withdrawn(id, requestDto);

        return new ResponseEntity<>(isWithdrawnResponseDto, HttpStatus.OK);
    }

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

package org.example.newsfeedteamproject.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.newsfeedteamproject.user.dto.UserRequestDto;
import org.example.newsfeedteamproject.user.dto.UserResponseDto;
import org.example.newsfeedteamproject.user.dto.*;
import org.example.newsfeedteamproject.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 회원가입 API
     * @param requestDto 가입할 회원 정보
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signUp(
            @Validated @ModelAttribute UserRequestDto requestDto
    ) throws IOException {

        UserResponseDto userResponseDto = userService.UserSignUp(requestDto);

        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }

    /**
     * 회원 선택 조회 API
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> UserFindById(@PathVariable Long userId){

        UserResponseDto userResponseDto = userService.UserFindById(userId);


        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    /**
     * 회원 전체 조회 API
     * @return
     */
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> UserFindAll(){

        List<UserResponseDto> userResponseDtoList = userService.UserFindAll();

        return new ResponseEntity<>(userResponseDtoList, HttpStatus.OK);
    }

    /**
     * 회원 수정 API
     * @param userId
     * @param requestDto 수정 데이터
     * @return
     */
    @PutMapping("/{userId}")
    public ResponseEntity<Void> UserUpdate(
            @PathVariable Long userId,
            @Validated @RequestBody UserUpdateRequestDto requestDto
    ){

        userService.UserUpdate(userId, requestDto);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    /**
     * 회원 탈퇴 API
     * @param userId
     * @param requestDto
     * @return
     */
    @PatchMapping("/{userId}")
    public ResponseEntity<IsWithdrawnResponseDto> UserWithdrawn(
            @PathVariable Long userId,
            @Validated @RequestBody IsWithdrawnRequestDto requestDto){

        IsWithdrawnResponseDto isWithdrawnResponseDto = userService.UserWithdrawn(userId, requestDto);

        return new ResponseEntity<>(isWithdrawnResponseDto, HttpStatus.OK);
    }
}

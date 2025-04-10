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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 회원가입 API
     * @param requestDto 요청 회원 정보
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signUp(
            @Validated @ModelAttribute UserRequestDto requestDto
    ) throws IOException {

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
    public ResponseEntity<Void> update(
            @PathVariable Long id,
            @Validated @RequestBody UserUpdateRequestDto requestDto
    ){

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
            @Validated @RequestBody IsWithdrawnRequestDto requestDto){

        IsWithdrawnResponseDto isWithdrawnResponseDto = userService.withdrawn(id, requestDto);

        return new ResponseEntity<>(isWithdrawnResponseDto, HttpStatus.OK);
    }
}

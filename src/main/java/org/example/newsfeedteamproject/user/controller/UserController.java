package org.example.newsfeedteamproject.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.newsfeedteamproject.comment.dto.CommentResponseDto;
import org.example.newsfeedteamproject.comment.service.CommentService;
import org.example.newsfeedteamproject.post.dto.PostResponseDto;
import org.example.newsfeedteamproject.post.service.PostService;
import org.example.newsfeedteamproject.user.dto.isWithDrawDto.IsWithdrawnRequestDto;
import org.example.newsfeedteamproject.user.dto.isWithDrawDto.IsWithdrawnResponseDto;
import org.example.newsfeedteamproject.user.dto.userDto.UserResponseDto;
import org.example.newsfeedteamproject.user.dto.userDto.UserUpdateRequestDto;
import org.example.newsfeedteamproject.user.entity.User;
import org.example.newsfeedteamproject.user.repository.UserRepository;
import org.example.newsfeedteamproject.user.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;
    private final UserRepository userRepository;

    /**
     * 특정 유저를 조회할 수 있습니다.
     * @param user
     * @return
     */

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> UserFindById(@AuthenticationPrincipal(expression = "username") String email) throws IOException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        Long userId = user.getId();
        UserResponseDto userResponseDto = userService.UserFindById(userId);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    /**
     * 회원 수정 api
     * @param user
     * @param requestDto
     * @return
     */
    @PutMapping("/{userId}")
    public ResponseEntity<Void> UserUpdate(
            @AuthenticationPrincipal(expression = "username") String email,
            @Validated @RequestBody UserUpdateRequestDto requestDto) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        Long userId = user.getId();
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
            @AuthenticationPrincipal(expression = "username") String email,
            @Validated @RequestBody IsWithdrawnRequestDto requestDto){
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        Long userId = user.getId();
        IsWithdrawnResponseDto isWithdrawnResponseDto = userService.UserWithdrawn(userId, requestDto);
        return new ResponseEntity<>(isWithdrawnResponseDto, HttpStatus.OK);
    }

    /**
     * 특정 유저가 작성한 게시글들을 조회합니다.
     * @param postId
     * @param user
     * @return
     */
    @GetMapping("/{userId}/posts")
    public ResponseEntity<Slice<PostResponseDto>> getPostById(
            @AuthenticationPrincipal(expression = "username") String email,
            @PageableDefault(size = 10, direction = DESC) Pageable pageable) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        Long userId = user.getId();
        return new ResponseEntity<>(postService.getPostByUserId(userId, pageable), HttpStatus.OK);
    }

    /**
     * 특정 유저를 클릭하면 그 유저가 작성한 댓글을 스크롤 방식으로 보여줍니다.
     * @param userId
     * @param pageable
     * @return
     */

    @GetMapping("/{userId}/comments")
    public ResponseEntity<Slice<CommentResponseDto>> getCommentPage
    (@AuthenticationPrincipal(expression = "username") String email,
     @PageableDefault(size = 10, direction = DESC) Pageable pageable) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        Long userId = user.getId();
        return new ResponseEntity<>(commentService.getCommentsByUserId(userId, pageable), HttpStatus.OK);
    }
}

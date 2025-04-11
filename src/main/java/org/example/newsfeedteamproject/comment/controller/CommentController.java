package org.example.newsfeedteamproject.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.newsfeedteamproject.comment.dto.CommentRequestDto;
import org.example.newsfeedteamproject.comment.dto.CommentResponseDto;
import org.example.newsfeedteamproject.comment.service.CommentService;
import org.example.newsfeedteamproject.user.entity.User;
import org.example.newsfeedteamproject.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")

public class CommentController {

    private final CommentService commentService;
    private final UserRepository userRepository;


    /**
     * 특정 게시글에 달린 댓글을 수정합니다. 이때 수정에는 권한이 필요합니다.
     * @param userId
     * @param commentId
     * @param requestDto
     * @return
     */

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @AuthenticationPrincipal(expression = "username") String email,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentRequestDto requestDto
    ) {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));


        Long userId = user.getId();
        return new ResponseEntity<>(commentService.updateComment(userId, commentId, requestDto),HttpStatus.OK);
    }

    /**
     * 특정 게시글에 달린 댓글을 삭제합니다.
     * @param userId
     * @param commentId
     * @return
     */

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @AuthenticationPrincipal(expression = "username") String email,
            @PathVariable Long commentId) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        Long userId = user.getId();
        commentService.deleteComment(userId, commentId);
        return ResponseEntity.ok().build();
    }
}
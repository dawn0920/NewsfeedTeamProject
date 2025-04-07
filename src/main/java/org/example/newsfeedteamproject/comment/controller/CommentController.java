package org.example.newsfeedteamproject.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.newsfeedteamproject.comment.dto.CommentRequestDto;
import org.example.newsfeedteamproject.comment.dto.CommentResponseDto;
import org.example.newsfeedteamproject.comment.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/add")
    public ResponseEntity<CommentResponseDto> addComment(
            @SessionAttribute(name = "userId") Long userId,
            @RequestParam Long postId,
            @Valid @RequestBody CommentRequestDto requestDto
    ) {
        return ResponseEntity.ok(commentService.addComment(userId, postId, requestDto));
    }

    @GetMapping("/lists")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByPost(
            @RequestParam Long postId
    ) {
        return ResponseEntity.ok(commentService.getCommentsByPost(postId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseDto> getComment(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.getComment(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @SessionAttribute(name = "userId") Long userId,
            @PathVariable Long id,
            @Valid @RequestBody CommentRequestDto requestDto
    ) {
        return ResponseEntity.ok(commentService.updateComment(id, userId, requestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(
            @SessionAttribute(name = "userId") Long userId,
            @PathVariable Long id
    ) {
        commentService.deleteComment(id, userId);
        return ResponseEntity.ok().build();
    }
}
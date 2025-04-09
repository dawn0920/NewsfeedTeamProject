package org.example.newsfeedteamproject.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.newsfeedteamproject.comment.dto.CommentRequestDto;
import org.example.newsfeedteamproject.comment.dto.CommentResponseDto;
import org.example.newsfeedteamproject.comment.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor

public class CommentController {

    private final CommentService commentService;

    /**
     * id를 받아서 포스트를 찾아주고, 세션을 받아서 로그인 유무를 확인해준다음 댓글을 등록합니다.
     * @param userId
     * @param postId
     * @param requestDto
     * @return
     */

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentResponseDto> postComment
    (@SessionAttribute(name = "LOGIN_USER") Long userId,
     @PathVariable Long postId,
     @Valid @RequestBody CommentRequestDto requestDto) {
        return new ResponseEntity<>(commentService.addComment(userId, postId, requestDto),HttpStatus.CREATED);
    }

    /**
     * 특정 포스트에 달린 댓글을 보여줍니다.
     * @param postId
     * @return
     */

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentResponseDto>> findByComment(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.findByComment(postId));
    }

    /**
     * 특정 포스트에 달린 댓글을 페이징해서 보여줍니다.
     * @param postId
     * @param pageable
     * @return
     */

    @GetMapping("/posts/{postId}/comments/page")
    public ResponseEntity<Page<CommentResponseDto>> getPostPage
    (@PathVariable Long postId,
     @PageableDefault(size = 10, sort = "creatTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return new ResponseEntity<>(commentService.getCommentsByPost(postId, pageable), HttpStatus.OK);
    }

    /**
     * 유저 관련 페이지에서 유저가 작성한 댓글을 페이징해서 보여줍니다. 이 접근은 누구나 할 수 있습니다.
     * @param userId
     * @return
     */

    @GetMapping("/users/information/{userId}/comment")
    public ResponseEntity<Page<CommentResponseDto>> getCommentPage
    (@PathVariable Long userId,
     @PageableDefault(size = 10, sort = "creatTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return new ResponseEntity<>(commentService.getCommentsByUserId(userId, pageable), HttpStatus.OK);
    }

    /**
     * 특정 게시글에 달린 댓글을 수정합니다.
     * @param userId
     * @param commentId
     * @param requestDto
     * @return
     */
    @PutMapping("posts/{postId}/comment/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @SessionAttribute(name = "LOGIN_USER") Long userId,
            @PathVariable Long commentId,
            @PathVariable Long postId,
            @Valid @RequestBody CommentRequestDto requestDto
    ) {
        return new ResponseEntity<>(commentService.updateComment(postId, commentId, requestDto),HttpStatus.OK);
    }

    /**
     * 특정 게시글에 달린 댓글을 삭제합니다.
     * @param userId
     * @param commentId
     * @return
     */

    @DeleteMapping("posts/{postId}/comment/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @SessionAttribute(name = "LOGIN_USER") Long userId,
            @PathVariable Long commentId,
            @PathVariable Long postId
    ) {
        commentService.deleteComment(commentId, userId,postId);
        return ResponseEntity.ok().build();
    }
}
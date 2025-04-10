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
@RequestMapping("/comment")

public class CommentController {

    private final CommentService commentService;

    /**
     * id를 받아서 포스트를 찾아주고, 세션을 받아서 로그인 유무를 확인해준다음 댓글을 등록합니다.
     * @param userId
     * @param postId
     * @param requestDto
     * @return
     */

    @PostMapping("/posts/{postId}")
    public ResponseEntity<CommentResponseDto> postComment
    (@SessionAttribute(name = Const.LOGIN_USER) Long userId,
     @PathVariable Long postId,
     @Valid @RequestBody CommentRequestDto requestDto) {
        return new ResponseEntity<>(commentService.addComment(userId, postId, requestDto),HttpStatus.CREATED);
    }

    /**
     * 특정 포스트를 클릭하면 스크롤 방식으로 댓글을 보여줍니다.
     * @param postId
     * @param pageable
     * @return
     */

    @GetMapping("/posts/{postId}")
    public ResponseEntity<Slice<CommentResponseDto>> getCommentPageByPost
    (@PathVariable Long postId,
     @PageableDefault(size = 10, direction = DESC) Pageable pageable) {
        return new ResponseEntity<>(commentService.getCommentsByPost(postId, pageable), HttpStatus.OK);
    }

    /**
     * 특정 유저를 클릭하면 그 유저가 작성한 댓글을 스크롤 방식으로 보여줍니다.
     * @param userId
     * @param pageable
     * @return
     */

    @GetMapping("/users/{userId}")
    public ResponseEntity<Slice<CommentResponseDto>> getCommentPage
    (@PathVariable Long userId,
     @PageableDefault(size = 10, direction = DESC) Pageable pageable) {
        return new ResponseEntity<>(commentService.getCommentsByUserId(userId, pageable), HttpStatus.OK);
    }

    /**
     * 특정 게시글에 달린 댓글을 수정합니다. 이때 수정에는 권한이 필요합니다.
     * @param userId
     * @param commentId
     * @param requestDto
     * @return
     */
    @PutMapping("posts/{postId}/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @SessionAttribute(name = Const.LOGIN_USER)  Long userId,
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

    @DeleteMapping("posts/{postId}/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @SessionAttribute(name = Const.LOGIN_USER) Long userId,
            @PathVariable Long commentId,
            @PathVariable Long postId
    ) {
        commentService.deleteComment(commentId, userId,postId);
        return ResponseEntity.ok().build();
    }
}
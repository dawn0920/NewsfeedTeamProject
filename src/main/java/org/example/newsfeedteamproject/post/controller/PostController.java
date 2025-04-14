package org.example.newsfeedteamproject.post.controller;

import jakarta.persistence.EntityListeners;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.newsfeedteamproject.comment.dto.CommentRequestDto;
import org.example.newsfeedteamproject.comment.dto.CommentResponseDto;
import org.example.newsfeedteamproject.comment.service.CommentService;
import org.example.newsfeedteamproject.post.dto.PostRequestDto;
import org.example.newsfeedteamproject.post.dto.PostResponseDto;
import org.example.newsfeedteamproject.post.service.PostService;
import org.example.newsfeedteamproject.user.entity.User;
import org.example.newsfeedteamproject.user.repository.UserRepository;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * 게시글 관련 HTTP 요청을 처리하는 컨트롤러 클래스입니다.
 * 작성, 전체 조회, 단건 조회, 수정, 삭제 기능을 제공합니다.
 */
@RestController
@RequestMapping("/posts")
@EntityListeners(org.springframework.data.jpa.domain.support.AuditingEntityListener.class)
@RequiredArgsConstructor

public class PostController {

    private final PostService postService;
    private final CommentService commentService;
    private final UserRepository userRepository;

    /**
     * 게시글 생성
     * @param requestDto
     * @param email
     * @return
     */
    @PostMapping
    public ResponseEntity<?> savePost(
            @Valid @RequestBody PostRequestDto requestDto,
            @AuthenticationPrincipal(expression = "username") String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Long userId = user.getId();
        PostResponseDto savedPost = postService.savePost(requestDto, userId);
        return ResponseEntity.ok(savedPost);
    }

    /**
     * 특정 게시글을 ID로 조회합니다. -> userId의 의도를 잘 몰라서 패스해뒀습니다! 나중에 설명 좀 해주세요!
     * @param userId 특정 유저의 ID
     * @param postId 조회할 게시글 ID
     * @return 게시글 응답 데이터
     */
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long postId) {
        PostResponseDto post = postService.getPostById(postId);
        return ResponseEntity.ok(post);
    }

    /**
     * 게시글을 수정합니다.
     *
     * @param postId     수정할 게시글 ID
     * @param requestDto 수정할 게시글 데이터
     * @return 수정된 게시글 응답 데이터
     */
    @PutMapping("/{postId}")
    public ResponseEntity<?> updatePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal(expression = "username") String email,
            @RequestBody PostRequestDto requestDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Long userId = user.getId();
        PostResponseDto updatedPost = postService.updatePost(postId, requestDto, userId);
        return ResponseEntity.ok(updatedPost);
    }

    /**
     * 게시글을 삭제합니다.
     *
     * @param postId 삭제할 게시글 ID
     * @param email 세션에 저장된 로그인 사용자 ID
     * @return HTTP 204 No Content 응답
     */
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId, @AuthenticationPrincipal(expression = "username")String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Long userId = user.getId();

        postService.deletePost(postId, userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * id를 받아서 포스트를 찾아주고, 댓글을 등록합니다.
     * @param email
     * @param postId
     * @param requestDto
     * @return
     */

    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentResponseDto> postComment
    (@AuthenticationPrincipal(expression = "username")String email,
     @PathVariable Long postId,
     @Valid @RequestBody CommentRequestDto requestDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Long userId = user.getId();
        return new ResponseEntity<>(commentService.addComment(userId, postId, requestDto),HttpStatus.CREATED);
    }

    /**
     * 특정 포스트를 클릭하면 스크롤 방식으로 댓글을 보여줍니다.
     * @param postId
     * @param pageable
     * @return
     */

    @GetMapping("/{postId}/comments")
    public ResponseEntity<Slice<CommentResponseDto>> getCommentPageByPost
    (@PathVariable Long postId,
     @PageableDefault(size = 10, sort = "creatTime", direction = DESC) Pageable pageable) {
        return new ResponseEntity<>(commentService.getCommentsByPost(postId, pageable), HttpStatus.OK);
    }

    /**
     * 전체 글 조회
     */
    @GetMapping
    public ResponseEntity<Slice<PostResponseDto>> getAllPosts(
            @PageableDefault(size = 10, sort = "creatTime", direction = DESC) Pageable pageable) {
        Slice<PostResponseDto> posts = postService.getAllPostList(pageable);
        return ResponseEntity.ok(posts);
    }

}

package org.example.newsfeedteamproject.post.controller;

import jakarta.persistence.EntityListeners;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.newsfeedteamproject.global.consts.Const;
import org.example.newsfeedteamproject.post.dto.PostRequestDto;
import org.example.newsfeedteamproject.post.dto.PostResponseDto;
import org.example.newsfeedteamproject.post.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * 게시글 생성
     *
     * @param requestDto 게시글 작성 요청 데이터
     * @param userId     세션에 저장된 로그인 사용자 ID
     * @return 생성된 게시글 응답 데이터
     */
    @PostMapping
    public ResponseEntity<?> savePost(
            @Valid @RequestBody PostRequestDto requestDto,
            @SessionAttribute(name = Const.LOGIN_USER) Long userId) {

        PostResponseDto savedPost = postService.savePost(requestDto, userId);
        return ResponseEntity.ok(savedPost);
    }

    /**
     * 전체 게시글 목록을 조회합니다.
     *
     * @return 게시글 목록
     */
    @GetMapping
    public ResponseEntity<Page<PostResponseDto>> getAllPosts(
            @PageableDefault(size = 10, sort = "creatTime", direction = org.springframework.data.domain.Sort.Direction.DESC)
            Pageable pageable) {
        Page<PostResponseDto> posts = postService.getAllPosts(pageable);
        return ResponseEntity.ok(posts);
    }

    /**
     * 특정 게시글을 ID로 조회합니다.
     *
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
     * @param userId     세션에 저장된 로그인 사용자 ID
     * @param requestDto 수정할 게시글 데이터
     * @return 수정된 게시글 응답 데이터
     */
    @PutMapping("/{postId}")
    public ResponseEntity<?> updatePost(
            @PathVariable Long postId,
            @SessionAttribute(name = Const.LOGIN_USER) Long userId,
            @RequestBody PostRequestDto requestDto) {

        PostResponseDto updatedPost = postService.updatePost(postId, requestDto, userId);
        return ResponseEntity.ok(updatedPost);
    }

    /**
     * 게시글을 삭제합니다.
     *
     * @param postId 삭제할 게시글 ID
     * @param userId 세션에 저장된 로그인 사용자 ID
     * @return HTTP 204 No Content 응답
     */
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId, @SessionAttribute(name = Const.LOGIN_USER) Long userId) {
        postService.deletePost(postId, userId);
        return ResponseEntity.noContent().build();
    }
}

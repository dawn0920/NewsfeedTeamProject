package org.example.newsfeedteamproject.post.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.newsfeedteamproject.global.consts.Const;
import org.example.newsfeedteamproject.post.dto.PostRequestDto;
import org.example.newsfeedteamproject.post.dto.PostResponseDto;
import org.example.newsfeedteamproject.post.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor

public class PostController {

private final PostService postService;

    /**
     * 포스트 작성 매핑
     * @param requestDto
     * @param userId
     * @return
     */

@PostMapping
    public ResponseEntity<?> savePost(
        @Valid @RequestBody PostRequestDto requestDto,
        @SessionAttribute(name = Const.LOGIN_USER) Long userId) {

    PostResponseDto savedPost =postService.savePost(requestDto,userId);
    return ResponseEntity.ok(savedPost);
}

    /**
     * 게시글 조회
     * @return
     */

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getAllPosts(){
    List<PostResponseDto> posts = postService.getAllPosts();
    return ResponseEntity.ok(posts);
}

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long id) {

    PostResponseDto post =postService.getPostById(id);

    return ResponseEntity.ok(post);

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(
            @PathVariable Long id,
            @SessionAttribute(name = Const.LOGIN_USER) Long userId,
            @RequestBody PostRequestDto requestDto) {

   PostResponseDto updatedPost = postService.updatePost(id, requestDto, userId);
    return ResponseEntity.ok(updatedPost);
    }

@DeleteMapping
    public ResponseEntity<?> deletePost(@PathVariable Long id , @SessionAttribute(name = Const.LOGIN_USER) Long userId){
    postService.deletePost(id, userId);
    return ResponseEntity.noContent().build();
    }
}

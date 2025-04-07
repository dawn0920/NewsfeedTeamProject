package org.example.newsfeedteamproject.post.controller;

import lombok.RequiredArgsConstructor;
import org.example.newsfeedteamproject.post.dto.PostRequestDto;
import org.example.newsfeedteamproject.post.dto.PostResponseDto;
import org.example.newsfeedteamproject.post.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

private final PostService postService;
@PostMapping
    public ResponseEntity<PostResponseDto> savePost(
        @RequestBody PostRequestDto requestDto,
        @RequestParam Long userId

        ){
    PostResponseDto savedPost = postService.savePost(requestDto,userId);
    return ResponseEntity.ok(savedPost);
}

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getAllPosts(){
    List<PostResponseDto> posts = postService.getAllPosts();
    return ResponseEntity.ok(posts);
}

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long id){
    PostResponseDto post =postService.getPostById(id);
    return ResponseEntity.ok(post);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePost(
            @PathVariable Long id,
            @RequestBody PostRequestDto requestDto
    ){
    PostResponseDto updatedPost = postService.updatePost(id, requestDto);
    return ResponseEntity.ok(updatedPost);
    }
@DeleteMapping
    public ResponseEntity<Void> deletePost(@PathVariable Long id){
    postService.deletePost(id);
    return ResponseEntity.noContent().build();
    }
}

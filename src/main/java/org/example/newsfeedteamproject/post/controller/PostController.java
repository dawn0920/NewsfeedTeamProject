package org.example.newsfeedteamproject.post.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.newsfeedteamproject.post.dto.PostRequestDto;
import org.example.newsfeedteamproject.post.dto.PostResponseDto;
import org.example.newsfeedteamproject.post.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

private final PostService postService;
@PostMapping
    public ResponseEntity<?> savePost(
        @RequestBody PostRequestDto requestDto,
        HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if(session == null || session.getAttribute("userId")==null){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다");
    }
    Long userId = (Long) session.getAttribute("userId");
    PostResponseDto savedPost =postService.savePost(requestDto,userId);
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
    public ResponseEntity<?> updatePost(
            @PathVariable Long id,
            @RequestBody PostRequestDto requestDto,
            HttpServletRequest request) {
   HttpSession session = request.getSession(false);
   if(session == null || session.getAttribute("userId")==null) {
       return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다");
   }
   Long userId = (Long) session.getAttribute("userId");
   PostResponseDto updatedPost = postService.updatePost(id, requestDto, userId);
    return ResponseEntity.ok(updatedPost);
    }
@DeleteMapping
    public ResponseEntity<?> deletePost(@PathVariable Long id ,
                                           HttpServletRequest request){
    HttpSession session = request.getSession(false);
    if(session == null||session.getAttribute("userId")==null){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
    }
    Long userId = (Long)session.getAttribute("userId");
    postService.deletePost(id, userId);
    return ResponseEntity.noContent().build();
    }
}

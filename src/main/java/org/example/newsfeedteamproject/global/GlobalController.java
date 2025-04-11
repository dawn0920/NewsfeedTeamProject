package org.example.newsfeedteamproject.global;

import lombok.RequiredArgsConstructor;
import org.example.newsfeedteamproject.comment.dto.CommentResponseDto;
import org.example.newsfeedteamproject.comment.service.CommentService;
import org.example.newsfeedteamproject.post.dto.PostResponseDto;
import org.example.newsfeedteamproject.post.service.PostService;
import org.example.newsfeedteamproject.user.dto.userDto.UserResponseDto;
import org.example.newsfeedteamproject.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")

public class GlobalController {
    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;

    /**
     * 회원 전체 조회 API
     * 개인적으로 이 부분은 어떤식으로 조회되는지 목적을 명확히 하는 게 더 좋을 것 같습니다.
     * 어드민이 유저를 조회? 유저가 유저를 조회?
     * 어드민이면 컨트롤러도 별개로 나와야하고, 인증과 인가를 다르게 받아야할 필요가 있습니다.
     * @return
     */
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> UserFindAll(){
        List<UserResponseDto> userResponseDtoList = userService.UserFindAll();
        return new ResponseEntity<>(userResponseDtoList, HttpStatus.OK);
    }

    /**
     * 전체 글 조회
     */
    @GetMapping("/posts")
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        List<PostResponseDto> posts = postService.getAllPostList();
        return ResponseEntity.ok(posts);
    }

    /**
     * 전체 댓글 조회
     * @return
     */

    @GetMapping("/comments")
    public ResponseEntity<List<CommentResponseDto>> getAllComment () {
        List<CommentResponseDto> foundCommentAll = commentService.getAllCommentService();
        return new ResponseEntity<>(foundCommentAll, HttpStatus.OK);
    }
}

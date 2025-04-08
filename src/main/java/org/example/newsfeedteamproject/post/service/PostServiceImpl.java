package org.example.newsfeedteamproject.post.service;

import org.example.newsfeedteamproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.example.newsfeedteamproject.post.dto.PostRequestDto;
import org.example.newsfeedteamproject.post.dto.PostResponseDto;
import org.example.newsfeedteamproject.post.entity.Post;
import org.example.newsfeedteamproject.post.repository.PostRepository;
import org.example.newsfeedteamproject.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 게시글 관련 비즈니스 로직을 구현한 서비스 클래스입니다.
 */
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;


    /**
     * 게시글을 작성합니다.
     *
     * @param requestDto 게시글 작성 요청 데이터
     * @param userId     작성자(로그인한 사용자)의 ID
     * @return 생성된 게시글의 응답 DTO
     */
    @Transactional
    @Override
    public PostResponseDto savePost(PostRequestDto requestDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        Post post = new Post(user, requestDto);
        Post savedPost = postRepository.save(post);

        return new PostResponseDto(savedPost);
    }

    /**
     * 전체 게시글 목록을 조회합니다.
     *
     * @return 게시글 응답 DTO 리스트
     */
    @Override
    public List<PostResponseDto> getAllPosts() {
        return postRepository.findAll().stream().map(PostResponseDto::new).collect(Collectors.toList());
    }

    /**
     * 특정 게시글을 조회합니다.
     *
     * @param postId 조회할 게시글의 ID
     * @return 게시글 응답 DTO
     */
    @Override
    public PostResponseDto getPostById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        return new PostResponseDto(post);
    }

    /**
     * 게시글을 수정합니다.
     *
     * @param postId     수정할 게시글의 ID
     * @param requestDto 수정할 데이터
     * @param userId     수정 요청자(로그인 사용자)의 ID
     * @return 수정된 게시글 응답 DTO
     */
    @Transactional
    @Override
    public PostResponseDto updatePost(Long postId, PostRequestDto requestDto, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        if (!post.getUser().getId().equals(userId)) {
            throw new RuntimeException("수정 권한이 없습니다.");
        }

        post.update(requestDto);
        return new PostResponseDto(post);
    }

    /**
     * 게시글을 삭제합니다.
     *
     * @param postId 삭제할 게시글의 ID
     * @param userId 삭제 요청자(로그인 사용자)의 ID
     */

    @Transactional
    @Override
    public void deletePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        if (!post.getUser().getId().equals(userId)) {
            throw new SecurityException("삭제 권한이 없습니다.");
        }

        postRepository.delete(post);
    }
}


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

@Service
@RequiredArgsConstructor

public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public PostResponseDto savePost(PostRequestDto requestDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        Post post = new Post(user, requestDto);
        Post savedPost = postRepository.save(post);

        return new PostResponseDto(savedPost);
    }

    @Override
    public List<PostResponseDto> getAllPosts() {
        return postRepository.findAll().stream().map(PostResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public PostResponseDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        return new PostResponseDto(post);
    }

    @Transactional
    @Override
    public PostResponseDto updatePost(Long id, PostRequestDto requestDto, Long userId) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        if (!post.getUser().getId().equals(userId)) {
            throw new RuntimeException("수정 권한이 없습니다.");
        }

        post.update(requestDto);
        return new PostResponseDto(post);
    }

    @Transactional
    @Override
    public void deletePost(Long id, Long userId) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        if (!post.getUser().getId().equals(userId)) {
            throw new SecurityException("삭제 권한이 없습니다.");
        }

        postRepository.delete(post);
    }
}


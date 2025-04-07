package org.example.newsfeedteamproject.post.service;

import org.example.newsfeedteamproject.post.dto.PostRequestDto;
import org.example.newsfeedteamproject.post.dto.PostResponseDto;

import java.util.List;

public interface PostService {

    PostResponseDto savePost(PostRequestDto requestDto, Long userId);
    List<PostResponseDto> getAllPosts();
    PostResponseDto getPostById(Long id);
    PostResponseDto updatePost(Long id,PostRequestDto requestDto, Long userId);
    void deletePost(Long id,Long userId);
}

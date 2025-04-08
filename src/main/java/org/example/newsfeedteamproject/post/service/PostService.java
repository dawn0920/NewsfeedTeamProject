package org.example.newsfeedteamproject.post.service;

import org.example.newsfeedteamproject.post.dto.PostRequestDto;
import org.example.newsfeedteamproject.post.dto.PostResponseDto;

import java.util.List;

/**
 * 게시글(Post) 관련 서비스 인터페이스
 * 게시글 생성, 조회, 수정, 삭제 기능을 정의
 */
public interface PostService {

    /**
     * 게시글을 저장합니다.
     *
     * @param requestDto 게시글 요청 데이터
     * @param userId     작성자의 사용자 ID
     * @return 생성된 게시글 응답 DTO
     */
    PostResponseDto savePost(PostRequestDto requestDto, Long userId);

    /**
     * 전체 게시글을 조회합니다.
     *
     * @return 게시글 응답 DTO 리스트
     */
    List<PostResponseDto> getAllPosts();

    /**
     * 게시글 ID로 단일 게시글을 조회합니다.
     *
     * @param postId 조회할 게시글의 ID
     * @return 게시글 응답 DTO
     */
    PostResponseDto getPostById(Long postId);

    /**
     * 게시글을 수정합니다.
     *
     * @param postId     수정할 게시글의 ID
     * @param requestDto 수정할 게시글 요청 데이터
     * @param userId     요청자의 사용자 ID
     * @return 수정된 게시글 응답 DTO
     */
    PostResponseDto updatePost(Long postId, PostRequestDto requestDto, Long userId);

    /**
     * 게시글을 삭제합니다.
     *
     * @param postId 삭제할 게시글의 ID
     * @param userId 요청자의 사용자 ID
     */
    void deletePost(Long postId, Long userId);
}

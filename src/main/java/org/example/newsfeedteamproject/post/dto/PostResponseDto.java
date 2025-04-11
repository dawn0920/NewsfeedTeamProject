package org.example.newsfeedteamproject.post.dto;

import lombok.Getter;
import org.example.newsfeedteamproject.post.entity.Post;
import org.example.newsfeedteamproject.user.entity.User;


import java.time.LocalDateTime;

/**
 * 게시글(Post) 정보를 응답할 때 사용하는 DTO 클래스입니다.
 * 클라이언트에게 필요한 게시글 데이터만 전달합니다.
 */
@Getter
public class PostResponseDto {

    /**
     * 게시글 ID
     */
    private final Long id;

    /**
     * 게시글 내용
     */
    private final String contents;

    /**
     * 게시글에 첨부된 이미지 (Base64 인코딩 문자열)
     */
    private final String img;

    /**
     * 해시태그 (예: #여행, #맛집)
     */
    private final String tag;

    /**
     * 멘션 (다른 유저 태그, 예: @username)
     */
    private final String mention;

    private final int postLike;

    /**
     * 게시글 작성자(유저)의 ID
     */
    private final Long userId;

    /**
     * 게시글 생성 시각
     */
    private final LocalDateTime createdAt;

    /**
     * 게시글 마지막 수정 시각
     */
    private final LocalDateTime updatedAt;

    /**
     * Post 엔티티를 기반으로 응답 DTO를 생성합니다.
     *
     * @param post 게시글 엔티티 객체
     */
    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.contents = post.getContents();
        this.img = post.getImg();
        this.tag = post.getTag();
        this.mention = post.getMention();
        this.userId = post.getUser().getId();
        this.createdAt = post.getCreatTime();
        this.updatedAt = post.getModifiedTime();
        this.postLike = post.getPostLike();
    }

    /**
     * 게시글 단건 조회를 위한 전용 생성자
     * @param post 게시글 엔티티 객체
     * @param user 유저 엔티티 객체
     */
    public PostResponseDto(Post post, User user) {
        this.id = post.getId();
        this.contents = post.getContents();
        this.img = post.getImg();
        this.tag = post.getTag();
        this.mention = post.getMention();
        this.userId = user.getId();
        this.createdAt = post.getCreatTime();
        this.updatedAt = post.getModifiedTime();
        this.postLike = post.getPostLike();
    }

}

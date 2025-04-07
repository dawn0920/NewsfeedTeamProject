package org.example.newsfeedteamproject.post.dto;

import lombok.Getter;


import java.time.LocalDateTime;

/**
 * 포스트 정보를 응답할때 사용하는 dto 클래스입니다.
 */
@Getter
public class PostResponseDto {


    /**
     * 게시글 식별자
     */
    private final Long id;

    /**
     * 게시글 내용 
     */
    private final String contents;

    /**
     * base64 이미지
     */
    private final String img;

    /**
     * 해시 태그
     */
    private final String tag;

    /**
     * 멘션
     */
    private final String mention;

    /**
     * 좋아요 상태
     */
    private final boolean like;

    /**
     * 프로필 id 외래키
     */
    private final Long profileId;

    /**
     * 생성 시각
     */
    private final LocalDateTime createdAt;

    /**
     * 수정 시각
     */
    private final LocalDateTime updatedAt;

    public PostResponseDto(Long id,String contents,String img, String tag, String mention,Boolean like, LocalDateTime createdAt,LocalDateTime updatedAt,Long profileId ){
        this.id = id;
        this.contents=contents;
        this.img = img;
        this.tag = tag;
        this.mention=mention;
        this.like=like;
        this.profileId=profileId;
        this.createdAt=createdAt;
        this.updatedAt=updatedAt;
    }

}

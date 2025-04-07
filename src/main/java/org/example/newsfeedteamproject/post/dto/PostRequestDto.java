package org.example.newsfeedteamproject.post.dto;

import lombok.Getter;

/**
 * 포스트 crud를 위한 요청 DTO 클래스 입니다.
 */
@Getter
public class PostRequestDto {

    /**
     * 게시글 내용 (필수)
     */
    private String contents;

    /**
     * 해시 태그
     */
    private String tag;

    /**
     * base64 이미지
     */
    private String img;

    /**
     * 멘션
     */
    private String mention;

    public PostRequestDto(String contents, String tag, String img, String mention){
        this.contents=contents;
        this.tag=tag;
        this.img=img;
        this.mention=mention;
    }
}

package org.example.newsfeedteamproject.post.dto;

import lombok.Getter;

/**
 * 게시글 생성 및 수정을 위한 요청 DTO 클래스입니다.
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
     * 이미지 (Base64 인코딩된 문자열, 선택)
     */
    private String img;

    /**
     * 멘션 (선택, 사용자 태그)
     */
    private String mention;

    /**
     * 모든 필드를 초기화하는 생성자입니다.
     *
     * @param contents 게시글 내용
     * @param tag      해시태그
     * @param img      Base64 이미지 문자열
     * @param mention  멘션(사용자 태그)
     */
    public PostRequestDto(String contents, String tag, String img, String mention){
        this.contents=contents;
        this.tag=tag;
        this.img=img;
        this.mention=mention;
    }
}

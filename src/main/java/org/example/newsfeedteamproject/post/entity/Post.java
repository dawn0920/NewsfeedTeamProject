package org.example.newsfeedteamproject.post.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.newsfeedteamproject.entity.BaseEntity;
import org.example.newsfeedteamproject.post.dto.PostRequestDto;
import org.example.newsfeedteamproject.user.entity.User;
/**
 * 포스트 정보를 나타내는 엔티티 클래스 입니다.
 * BaseEntity의 생성 시간 및 수정 시간을 상속 받습니다.
 */
@Getter
@Entity
@NoArgsConstructor
@Table(name = "post")
public class Post extends BaseEntity {

    /**
     * 게시글 식별자
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 게시글 내용
     */
    @Column(nullable = false)
    private String contents;

    /**
     * base64 이미지
     */
    @Column(nullable = false)
    private String img;

    /**
     * 해시 태그
     */
    private String tag;

    /**
     * 멘션
     */
    private String mention;

    /**
     * 좋아요 상태
     */
//    private boolean like;

    /**
     * 프로필 id 외래키
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",referencedColumnName = "id", nullable = false)
    private User user;

    public Post(User user, PostRequestDto dto){
        this.user = user;
        this.contents = dto.getContents();
        this.img=dto.getImg();
        this.tag=dto.getMention();
        this.mention= dto.getMention();
//        this.like = false;
    }

    public void update(PostRequestDto dto){
        this.contents = dto.getContents();
        this.img = dto.getImg();
        this.tag = dto.getTag();
        this.mention = dto.getMention();
    }

}

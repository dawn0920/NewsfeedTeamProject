package org.example.newsfeedteamproject.user.dto;

import lombok.Getter;
import org.example.newsfeedteamproject.entity.BaseEntity;

import java.time.LocalDateTime;

@Getter
public class UserResponseDto {

    private final Long id;

    private final String email;

    private final String userId;

    private final String name;

    private final String intro;

    private final String profileImg;

    private final String birthday;

    private final int follow;

    private final int following;

    private final String phone;

    private final LocalDateTime createdTime;

    private final LocalDateTime modifiedTime;

    public UserResponseDto(Long id, String email, String userId, String name, String intro, String profileImg, String birthday, int follow, int following, String phone, LocalDateTime createdTime, LocalDateTime modifiedTime) {
        this.id = id;
        this.email = email;
        this.userId = userId;
        this.name = name;
        this.intro = intro;
        this.profileImg = profileImg;
        this.birthday = birthday;
        this.follow = follow;
        this.following = following;
        this.phone = phone;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
    }
}

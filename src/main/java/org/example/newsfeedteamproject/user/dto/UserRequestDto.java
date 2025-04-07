package org.example.newsfeedteamproject.user.dto;

import lombok.Getter;
import org.example.newsfeedteamproject.entity.BaseEntity;

import java.time.LocalDateTime;

@Getter
public class UserRequestDto {

    private final Long id;

    private final String password;

    private final String email;

    private final String userId;

    private final String name;

    private final String intro;

    private final String profileImg;

    private final String birthday;

    private final String phone;

    public UserRequestDto(Long id, String password, String email, String userId, String name, String intro, String profileImg, String birthday, String phone) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.userId = userId;
        this.name = name;
        this.intro = intro;
        this.profileImg = profileImg;
        this.birthday = birthday;
        this.phone = phone;
    }
}

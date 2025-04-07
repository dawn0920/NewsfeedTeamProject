package org.example.newsfeedteamproject.user.dto;

import lombok.Getter;
import org.example.newsfeedteamproject.entity.BaseEntity;
import org.example.newsfeedteamproject.user.entity.User;

import java.time.LocalDateTime;

@Getter
public class UserRequestDto {

    private final String email;

    private final String password;

    private final String userId;

    private final String name;

    private final String intro;

    private final String profileImg;

    private final String birthday;

    private final String phone;

    public UserRequestDto(String email, String password, String userId, String name, String intro, String profileImg, String birthday, String phone) {
        this.email = email;
        this.password = password;
        this.userId = userId;
        this.name = name;
        this.intro = intro;
        this.profileImg = profileImg;
        this.birthday = birthday;
        this.phone = phone;
    }
}

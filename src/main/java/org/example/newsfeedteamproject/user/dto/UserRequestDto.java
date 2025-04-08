package org.example.newsfeedteamproject.user.dto;

import lombok.Getter;

@Getter
public class UserRequestDto {

    private final String email;

    private final String password;

    private final String userRefId;

    private final String name;

    private final String intro;

    private final String profileImg;

    private final String birthday;

    private final String phone;

    public UserRequestDto(String email, String password, String userRefId, String name, String intro, String profileImg, String birthday, String phone) {
        this.email = email;
        this.password = password;
        this.userRefId = userRefId;
        this.name = name;
        this.intro = intro;
        this.profileImg = profileImg;
        this.birthday = birthday;
        this.phone = phone;
    }
}

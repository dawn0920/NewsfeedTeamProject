package org.example.newsfeedteamproject.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserUpdateRequestDto {

    @Email(message = "유요한 이메일 형식이 아닙니다.")
    private final String email;

    @NotBlank(message = "비밀번호는 필수 입력값 입니다.")
    private final String password;

    private final String userRefId;

    private final String name;

    private final String intro;

    private final String profileImg;

    private final String birthday;

    private final String phone;

    public UserUpdateRequestDto(String email, String password, String userRefId, String name, String intro, String profileImg, String birthday, String phone) {
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

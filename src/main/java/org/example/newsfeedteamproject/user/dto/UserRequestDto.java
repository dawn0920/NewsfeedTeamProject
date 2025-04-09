package org.example.newsfeedteamproject.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserRequestDto {

    @NotBlank(message = "이메일은 필수 입력값 입니다.")
    @Email(message = "유요한 이메일 형식이 아닙니다.")
    private final String email;

    @NotBlank(message = "비밀번호는 필수 입력값 입니다.")
    private final String password;

    @NotBlank(message = "사용할 아이디를 입력해주세요")
    private final String userRefId;

    @NotBlank(message = "이름을 입력해주세요")
    private final String name;

    private final String intro;

    private final String profileImg;

    @NotBlank(message = "생년월일을 입력해주세요")
    private final String birthday;

    @NotBlank(message = "전화번호를 입력해주세요")
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

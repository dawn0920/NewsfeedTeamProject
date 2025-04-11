package org.example.newsfeedteamproject.user.dto.userRegistration;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter

public class LogOutDto {

    @NotBlank(message = "이메일을 입력해주세요")
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    private final String email;

    private final String accessToken;

    public LogOutDto(String email, String accessToken) {
        this.email = email;
        this.accessToken = accessToken;
    }
}

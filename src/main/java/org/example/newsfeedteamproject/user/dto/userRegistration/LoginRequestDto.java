package org.example.newsfeedteamproject.user.dto.userRegistration;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter

public class LoginRequestDto {

    @NotBlank(message = "이메일을 입력해주세요")
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    private final String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private final String password;

    public LoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}

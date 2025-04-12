package org.example.newsfeedteamproject.user.dto.userRegistration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.newsfeedteamproject.user.entity.User;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder

public class SignUpDto {

    @NotBlank(message = "이메일은 필수 입력값 입니다.")
    @Email(message = "유요한 이메일 형식이 아닙니다.")
    private final String email;

    @NotBlank(message = "비밀번호는 필수 입력값 입니다.")
    private final String password;

    /**
     * 유저가 관리하는 개인 아이디
     */

    @NotBlank(message = "사용할 아이디를 입력해주세요")
    private final String userRefId;

    @NotBlank(message = "이름을 입력해주세요")
    private final String name;

    @NotBlank(message = "생년월일을 입력해주세요")
    private final String birthday;

    @NotBlank(message = "전화번호를 입력해주세요")
    private final String phone;

    @JsonIgnore
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    public User toEntity(String encodedPassword, List<String> roles) {
        return User.builder()
                .email(email)
                .password(encodedPassword)
                .userRefId(userRefId)
                .name(name)
                .birthday(birthday)
                .phone(phone)
                .roles(roles)
                .build();
    }
}

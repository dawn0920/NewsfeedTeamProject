package org.example.newsfeedteamproject.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

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

    @NotBlank(message = "생년월일을 입력해주세요")
    private final String birthday;

    @NotBlank(message = "전화번호를 입력해주세요")
    private final String phone;

    private final MultipartFile file;

    public UserRequestDto(String email, String password, String userRefId, String name, String birthday, String phone, MultipartFile file) {
        this.email = email;
        this.password = password;
        this.userRefId = userRefId;
        this.name = name;
        this.birthday = birthday;
        this.phone = phone;
        this.file = file;
    }
}

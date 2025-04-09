package org.example.newsfeedteamproject.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class IsWithdrawnRequestDto {

    @NotBlank(message = "비밀번호를 입력해주세요")
    private final String password;

    private final boolean withdrawn;

    public IsWithdrawnRequestDto(String password, boolean withdrawn) {
        this.password = password;
        this.withdrawn = withdrawn;
    }
}

package org.example.newsfeedteamproject.user.dto;

import lombok.Getter;

@Getter
public class IsWithdrawnRequestDto {

    private final String password;

    private final boolean withdrawn;

    public IsWithdrawnRequestDto(String password, boolean withdrawn) {
        this.password = password;
        this.withdrawn = withdrawn;
    }
}

package org.example.newsfeedteamproject.user.dto;

import lombok.Getter;

@Getter
public class IsWithdrawnRequestDto {

    private final boolean isWithdrawn;

    public IsWithdrawnRequestDto(boolean isWithdrawn) {
        this.isWithdrawn = isWithdrawn;
    }
}

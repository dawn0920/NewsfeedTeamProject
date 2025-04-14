package org.example.newsfeedteamproject.user.dto;

import lombok.Getter;

@Getter
public class IsWithdrawnResponseDto {

    private final Long id;

    private final boolean withdrawn;

    public IsWithdrawnResponseDto(Long id, boolean withdrawn) {
        this.id = id;
        this.withdrawn = withdrawn;
    }
}

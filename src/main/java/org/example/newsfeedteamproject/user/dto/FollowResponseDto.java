package org.example.newsfeedteamproject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FollowResponseDto {
    private boolean success;
    private int follwoCount;
}

package org.example.newsfeedteamproject.user.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class FollowListReponseDto {
    private Long userId;
    private String userName;
    private List<FollowDto> followings;
    private List<FollowDto> followers;
}

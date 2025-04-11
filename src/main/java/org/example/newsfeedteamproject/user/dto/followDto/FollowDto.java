package org.example.newsfeedteamproject.user.dto.followDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.newsfeedteamproject.user.entity.User;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FollowDto {
    private Long userId;
    private String profileImg;
    private String name;
    private String userRefId;
    private String intro;

    public FollowDto(User user) {
        this.userId = user.getId();
        this.profileImg = user.getFileName();
        this.name = user.getName();
        this.userRefId = user.getUserRefId();
        this.intro = user.getIntro();
    }
}

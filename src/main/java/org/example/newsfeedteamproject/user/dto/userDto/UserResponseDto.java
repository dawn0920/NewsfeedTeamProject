package org.example.newsfeedteamproject.user.dto.userDto;

import lombok.Getter;
import org.example.newsfeedteamproject.user.entity.User;

import java.time.LocalDateTime;

@Getter
public class UserResponseDto {

    private final Long id;

    private final String email;

    private final String userRefId;

    private final String name;

    private final String intro;

    private final String birthday;

    private final int follow;

    private final int following;

    private final String phone;

    private final LocalDateTime createdTime;

    private final LocalDateTime modifiedTime;

    private final String fileName;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.userRefId = user.getUserRefId();
        this.name = user.getName();
        this.intro = user.getIntro();
        this.birthday = user.getBirthday();
        this.follow = user.getFollow();
        this.following = user.getFollowing();
        this.phone = user.getPhone();
        this.createdTime = user.getCreatTime();
        this.modifiedTime = user.getModifiedTime();
        this.fileName = user.getFileName();
    }

    public static UserResponseDto toDto(User user){
        return new UserResponseDto(user);
    }
}

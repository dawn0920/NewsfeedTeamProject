package org.example.newsfeedteamproject.user.dto.userRegistration;

import lombok.*;
import org.example.newsfeedteamproject.user.entity.User;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ResponseSignUpDto {

    private Long id;
    private String email;
    private String userRefId;
    private String name;
    private String birthday;
    private String phone;

    static public ResponseSignUpDto toDto(User user) {
        return ResponseSignUpDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .userRefId(user.getUserRefId())
                .name(user.getName())
                .birthday(user.getBirthday())
                .phone(user.getPhone())
                .build();
    }
}
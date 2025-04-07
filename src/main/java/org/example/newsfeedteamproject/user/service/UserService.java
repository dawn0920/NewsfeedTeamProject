package org.example.newsfeedteamproject.user.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeedteamproject.user.dto.UserRequestDto;
import org.example.newsfeedteamproject.user.dto.UserResponseDto;
import org.example.newsfeedteamproject.user.entity.User;
import org.example.newsfeedteamproject.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponseDto signUp(UserRequestDto requestDto) {

        User user = new User(
                requestDto.getEmail(),
                requestDto.getPassword(),
                requestDto.getUserId(),
                requestDto.getName(),
                requestDto.getIntro(),
                requestDto.getProfileImg(),
                requestDto.getBirthday(),
                requestDto.getPhone()
        );

       userRepository.save(user);

        return new UserResponseDto(
                user.getId(),
                user.getEmail(),
                user.getUserId(),
                user.getName(),
                user.getIntro(),
                user.getProfileImg(),
                user.getBirthday(),
                user.getFollow(),
                user.getFollowing(),
                user.getPhone(),
                user.getCreatTime(),
                user.getModifiedTime()
        );

    }
}

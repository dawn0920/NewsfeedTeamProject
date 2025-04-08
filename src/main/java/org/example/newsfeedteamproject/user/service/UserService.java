package org.example.newsfeedteamproject.user.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeedteamproject.user.dto.UserRequestDto;
import org.example.newsfeedteamproject.user.dto.UserResponseDto;
import org.example.newsfeedteamproject.user.entity.User;
import org.example.newsfeedteamproject.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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

    public UserResponseDto findById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dose not exist id = " + id)
        );

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

    public List<UserResponseDto> findAll() {

        return userRepository.findAll()
                .stream()
                .map(user -> new UserResponseDto(
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
                )).toList();
    }
}

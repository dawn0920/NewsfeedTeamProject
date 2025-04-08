package org.example.newsfeedteamproject.user.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeedteamproject.user.dto.IsWithdrawnRequestDto;
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

    public void update(Long id, UserRequestDto requestDto) {

        User user = userRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Dose not exist id = " + id
                        )
                );

        if(!requestDto.getPassword().equals(user.getPassword())){
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "패스워드가 일치하지 않습니다."
            );
        }

        String email = isNullOrEmpty(requestDto.getEmail()) ? user.getEmail() : requestDto.getEmail();
        String userId = isNullOrEmpty(requestDto.getUserId()) ? user.getUserId() : requestDto.getUserId();
        String name = isNullOrEmpty(requestDto.getName()) ? user.getName() : requestDto.getName();
        String intro = isNullOrEmpty(requestDto.getIntro()) ? user.getIntro() : requestDto.getIntro();
        String profileImg = isNullOrEmpty(requestDto.getProfileImg()) ? user.getProfileImg() : requestDto.getProfileImg();
        String birthday = isNullOrEmpty(requestDto.getBirthday()) ? user.getBirthday() : requestDto.getBirthday();
        String phone = isNullOrEmpty(requestDto.getPhone()) ? user.getPhone() : requestDto.getPhone();

        user.update(email, userId, name, intro, profileImg, birthday, phone);
    }

    /**
     * null 인지 값이 비어있는지 체크
     * @param str 수정할 데이터
     * @return 데이터가 비어있거나 null이면 true, 아니면 false
     */
    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public void withdrawn(Long id, IsWithdrawnRequestDto requestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Dose not exist id = " + id
                        )
                );

        user.withdrawn(requestDto.isWithdrawn());
    }
}

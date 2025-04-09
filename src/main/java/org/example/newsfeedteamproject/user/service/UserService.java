package org.example.newsfeedteamproject.user.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeedteamproject.user.dto.*;
import org.example.newsfeedteamproject.user.entity.User;
import org.example.newsfeedteamproject.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDto signUp(UserRequestDto requestDto) {

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        User user = new User(
                requestDto.getEmail(),
                encodedPassword,
                requestDto.getUserRefId(),
                requestDto.getName(),
                requestDto.getBirthday(),
                requestDto.getPhone()
        );

       userRepository.save(user);

        return new UserResponseDto(user);
    }

    public UserResponseDto findById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dose not exist id = " + id)
        );

        if(user.isWithdrawn()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 계정이 존재하지 않습니다.");
        }

        return new UserResponseDto(user);
    }

    public List<UserResponseDto> findAll() {

        return userRepository.findByWithdrawnFalse()
                .stream()
                .map(UserResponseDto::toDto).toList();
    }

    @Transactional
    public void update(Long id, UserUpdateRequestDto requestDto) {

        User user = userRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Dose not exist id = " + id
                        )
                );

        if(user.isWithdrawn()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 계정이 존재하지 않습니다.");
        }

        if(!passwordEncoder.matches(requestDto.getPassword(),user.getPassword())){
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "패스워드가 일치하지 않습니다."
            );
        }

        String email = isNullOrEmpty(requestDto.getEmail()) ? user.getEmail() : requestDto.getEmail();
        String userId = isNullOrEmpty(requestDto.getUserRefId()) ? user.getUserRefId() : requestDto.getUserRefId();
        String name = isNullOrEmpty(requestDto.getName()) ? user.getName() : requestDto.getName();
        String birthday = isNullOrEmpty(requestDto.getBirthday()) ? user.getBirthday() : requestDto.getBirthday();
        String phone = isNullOrEmpty(requestDto.getPhone()) ? user.getPhone() : requestDto.getPhone();

        user.update(email, userId, name, birthday, phone);
    }

    /**
     * null 인지 값이 비어있는지 체크
     * @param str 수정할 데이터
     * @return 데이터가 비어있거나 null이면 true, 아니면 false
     */
    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    @Transactional
    public IsWithdrawnResponseDto withdrawn(Long id, IsWithdrawnRequestDto requestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Dose not exist id = " + id
                        )
                );

        if(!passwordEncoder.matches(requestDto.getPassword(),user.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "패스워드가 일치하지 않습니다.");
        }

        user.withdrawn(requestDto.isWithdrawn());

        return new IsWithdrawnResponseDto(user.getId(), user.isWithdrawn());
    }

    public Long login(LoginRequestDto requestDto) {

        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "해당 이메일은 존재하지 않습니다."));

        if(!passwordEncoder.matches(requestDto.getPassword(),user.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "패스워드가 일치하지 않습니다.");
        }

        return user.getId();
    }

}

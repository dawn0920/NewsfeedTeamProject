package org.example.newsfeedteamproject.user.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeedteamproject.user.dto.*;
import org.example.newsfeedteamproject.user.entity.User;
import org.example.newsfeedteamproject.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Transactional
    public UserResponseDto signUp(UserRequestDto requestDto) throws IOException {


        String originalFilename = requestDto.getFile().getOriginalFilename();
        String filePath = uploadDir + File.separator + originalFilename;

        // 폴더 없으면 생성
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            System.out.println("업로드 폴더 생성 여부: " + created);
        }

        // 파일 저장
        File dest = new File(filePath);
        requestDto.getFile().transferTo(dest);

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        User user = new User(
                requestDto.getEmail(),
                encodedPassword,
                requestDto.getUserRefId(),
                requestDto.getName(),
                requestDto.getBirthday(),
                requestDto.getPhone(),
                originalFilename,
                filePath
        );

       userRepository.save(user);

        return new UserResponseDto(user);
    }

    /**
     *
     * @param id
     * @return
     */
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

    /**
     *
     * @param id
     * @param requestDto
     */
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

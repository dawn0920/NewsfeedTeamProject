package org.example.newsfeedteamproject.user.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeedteamproject.user.dto.*;
import org.example.newsfeedteamproject.user.entity.Follow;
import org.example.newsfeedteamproject.user.entity.User;
import org.example.newsfeedteamproject.user.repository.FollowRepository;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FollowRepository followRepository;

    //사진 업로드 경로 지정
    @Value("${file.upload-dir}")
    private String uploadDir;

    /**
     * 회원가입
     * @param requestDto 가입할 회원 정보 요청
     * @return
     * @throws IOException transferTo에 대한 예외 처리
     */
    @Transactional
    public UserResponseDto UserSignUp(UserRequestDto requestDto) throws IOException {

        // 파일명
        String originalFilename = requestDto.getFile().getOriginalFilename();
        // 파일 저장 경로
        String filePath = uploadDir + File.separator + originalFilename;

        // 폴더 없으면 생성
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            System.out.println("업로드 폴더 생성 여부: " + created);
        }

        // 파일 경로 객체 생성
        File dest = new File(filePath);
        /**
         * requestDto.getFile()로 사용자가 업로드한 MultipartFile을 가져오고
         * transferTo(dest)로 업로드된 임시 파일을 dest 경로에 복사(또는 이동)
         */
        requestDto.getFile().transferTo(dest);

        // 패스워드 엔코더
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

        // DB 저장
        userRepository.save(user);

        return new UserResponseDto(user);
    }

    /**
     * 회원 선택 조회
     * @param userId
     * @return
     */
    public UserResponseDto UserFindById(Long userId) {

        // 받아온 userId에 대한 user 객체 찾기
        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dose not exist id = " + userId)
        );

        // isWithdrawn 값이 true 이면 탈퇴된 계정이므로 예외처리
        if(user.isWithdrawn()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 계정이 존재하지 않습니다.");
        }

        // follow 정보를 새로고침
        List<Follow> followingsInfo = followRepository.findAllByFromUser(user);
        List<Follow> followerInfo = followRepository.findAllByToUser(user);

        // 조건 검색
        int followings = (int) followingsInfo.stream()
                .filter(f -> !f.getToUser().isWithdrawn())
                .count();

        int followers = (int) followerInfo.stream()
                .filter(f -> !f.getFromUser().isWithdrawn())
                .count();

        // 유저 정보에 반영
        user.setFollowCountByListSize(followers, followings);

        // 유저 정보 저장
        userRepository.save(user);


        return new UserResponseDto(user);
    }

    /**
     * 회원 전체 조회
     * @return
     */
    public List<UserResponseDto> UserFindAll() {

        // withdrawn 값이 false 인 user 만 찾아서 List 생성
        return userRepository.findByWithdrawnFalse()
                .stream()
                .map(UserResponseDto::toDto).toList();
    }

    /**
     * 회원 정보 변경
     * @param userId 유저 id
     * @param requestDto 변경할 회원 정보
     */
    @Transactional
    public void UserUpdate(Long userId, UserUpdateRequestDto requestDto) {

        // 받아온 userId에 대한 user 객체 찾기
        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dose not exist id = " + userId)
                );
        // isWithdrawn 값이 true 이면 탈퇴된 계정이므로 예외처리
        if(user.isWithdrawn()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 계정이 존재하지 않습니다.");
        }

        // 패스워드 본인 인증
        if(!passwordEncoder.matches(requestDto.getPassword(),user.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "패스워드가 일치하지 않습니다.");
        }

        /**
         * 수정하고 싶은 값만 입력해도 업데이트 가능
         * 전부 다 업데이트 할 이유 없음
         */
        String email = isNullOrEmpty(requestDto.getEmail()) ? user.getEmail() : requestDto.getEmail();
        String userRefId = isNullOrEmpty(requestDto.getUserRefId()) ? user.getUserRefId() : requestDto.getUserRefId();
        String name = isNullOrEmpty(requestDto.getName()) ? user.getName() : requestDto.getName();
        String birthday = isNullOrEmpty(requestDto.getBirthday()) ? user.getBirthday() : requestDto.getBirthday();
        String phone = isNullOrEmpty(requestDto.getPhone()) ? user.getPhone() : requestDto.getPhone();

        // 회원 정보 업데이트
        user.update(email, userRefId, name, birthday, phone);
    }

    /**
     * null 인지 값이 비어있는지 체크
     * @param str 수정할 데이터
     * @return 데이터가 비어있거나 null이면 true, 아니면 false
     */
    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * 회원 탈퇴 기능
     * @param userId 유저 id
     * @param requestDto isWithdrawn 값이 true 이면 탈퇴처리
     * @return
     */
    @Transactional
    public IsWithdrawnResponseDto UserWithdrawn(Long userId, IsWithdrawnRequestDto requestDto) {

        // 받아온 userId에 대한 user 객체 찾기
        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dose not exist id = " + userId)
                );

        // 패스워드 본인 인증
        if(!passwordEncoder.matches(requestDto.getPassword(),user.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "패스워드가 일치하지 않습니다.");
        }

        user.withdrawn(requestDto.isWithdrawn());

        return new IsWithdrawnResponseDto(user.getId(), user.isWithdrawn());
    }

    /**
     * 로그인
     * @param requestDto 로그인 정보
     * @return
     */
    public Long login(LoginRequestDto requestDto) {

        // 요청한 이메일이 DB에 있는지 찾고 있으면 user 객체로 생성
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "해당 이메일은 존재하지 않습니다."));

        // 패스워드 본인 인증
        if(!passwordEncoder.matches(requestDto.getPassword(),user.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "패스워드가 일치하지 않습니다.");
        }

        // 탈퇴된 계정 로그인 불가
        if(user.isWithdrawn()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 계정이 존재하지 않습니다.");
        }

        return user.getId();
    }

}

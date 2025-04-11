package org.example.newsfeedteamproject.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.newsfeedteamproject.global.common.jwt.JwtToken;
import org.example.newsfeedteamproject.global.common.jwt.JwtTokenProvider;
import org.example.newsfeedteamproject.global.error.CustomException;
import org.example.newsfeedteamproject.global.error.ExceptionCode;
import org.example.newsfeedteamproject.user.dto.isWithDrawDto.IsWithdrawnRequestDto;
import org.example.newsfeedteamproject.user.dto.isWithDrawDto.IsWithdrawnResponseDto;
import org.example.newsfeedteamproject.user.dto.userDto.UserResponseDto;
import org.example.newsfeedteamproject.user.dto.userDto.UserUpdateRequestDto;
import org.example.newsfeedteamproject.user.dto.userRegistration.LoginRequestDto;
import org.example.newsfeedteamproject.user.dto.userRegistration.ResponseSignUpDto;
import org.example.newsfeedteamproject.user.dto.userRegistration.SignUpDto;
import org.example.newsfeedteamproject.user.entity.Follow;
import org.example.newsfeedteamproject.user.entity.User;
import org.example.newsfeedteamproject.user.repository.FollowRepository;
import org.example.newsfeedteamproject.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.example.newsfeedteamproject.global.common.cofing.CacheNames;
import org.springframework.cache.annotation.Cacheable;
import org.example.newsfeedteamproject.global.common.cofing.RedisDao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j

public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FollowRepository followRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RedisDao redisDao;

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
    public ResponseSignUpDto signUpService(SignUpDto requestDto) {

        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }

        if (userRepository.existsByUserRefId(requestDto.getUserRefId())) {
            throw new RuntimeException("이미 존재하는 아이디 입니다.");
        }

        if(userRepository.existsByPhone(requestDto.getPhone())) {
            throw new RuntimeException("이미 가입된 전화번호 입니다.");
        }


        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        return ResponseSignUpDto.toDto(userRepository.save(requestDto.toEntity(encodedPassword, roles)));
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
                        () -> new CustomException(ExceptionCode.USER_NOT_FOUND)
        );

        // isWithdrawn 값이 true 이면 탈퇴된 계정이므로 예외처리
        if(user.isWithdrawn()){
            throw new CustomException(ExceptionCode.USER_NOT_FOUND);
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
                        () -> new CustomException(ExceptionCode.USER_NOT_FOUND)
                );
        // isWithdrawn 값이 true 이면 탈퇴된 계정이므로 예외처리
        if(user.isWithdrawn()){
            throw new CustomException(ExceptionCode.USER_NOT_FOUND);
        }

        // 패스워드 본인 인증
        if(!passwordEncoder.matches(requestDto.getPassword(),user.getPassword())){
            throw new CustomException(ExceptionCode.PASSWORD_NOT_FOUND);
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
                        () -> new CustomException(ExceptionCode.USER_NOT_FOUND)
                );

        // 패스워드 본인 인증
        if(!passwordEncoder.matches(requestDto.getPassword(),user.getPassword())){
            throw new CustomException(ExceptionCode.PASSWORD_NOT_FOUND);
        }

        user.withdrawn(requestDto.isWithdrawn());

        return new IsWithdrawnResponseDto(user.getId(), user.isWithdrawn());
    }

    /**
     * 로그인 기능
     * @param requestDto
     * @return
     */

    @Cacheable(cacheNames = CacheNames.LOGINUSER, key = "'login'+ #p0.getEmail()", unless = "#result== null")
    @jakarta.transaction.Transactional
    public JwtToken login(LoginRequestDto requestDto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);
        Long refreshTokenTTL = jwtTokenProvider.getExpiration(jwtToken.getRefreshToken());
        redisDao.setRefreshToken(requestDto.getEmail(), jwtToken.getRefreshToken(), refreshTokenTTL);
        return jwtToken;
    }

    /**
     * 로그아웃기능
     * @param requestDto
     * @return
     */

    @CacheEvict(cacheNames = CacheNames.LOGINUSER, key = "'login'+#p1")
    @Transactional
    public void logOut(String accessToken, String email) {

        Long expiration = jwtTokenProvider.getExpiration(accessToken);
        redisDao.setBlackList(accessToken, "logout", expiration);

        if (redisDao.hasKey(email)) {
            redisDao.deleteRefreshToken(email);
        } else {
            throw new IllegalArgumentException("이미 로그아웃한 유저입니다.");
        }
    }
}

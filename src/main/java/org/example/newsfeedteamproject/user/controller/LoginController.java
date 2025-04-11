package org.example.newsfeedteamproject.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.newsfeedteamproject.global.common.jwt.JwtToken;
import org.example.newsfeedteamproject.user.dto.userRegistration.LogOutDto;
import org.example.newsfeedteamproject.user.dto.userRegistration.LoginRequestDto;
import org.example.newsfeedteamproject.user.dto.userRegistration.ResponseSignUpDto;
import org.example.newsfeedteamproject.user.dto.userRegistration.SignUpDto;
import org.example.newsfeedteamproject.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    /**
     * 회원가입 API
     * @param requestDto 가입할 회원 정보
     * @return
     */

    @PostMapping("/signup")
    public ResponseEntity<ResponseSignUpDto> signUp(@Validated @RequestBody SignUpDto requestDto) {
        ResponseSignUpDto responseSignUpDto = userService.signUpService(requestDto);
        return ResponseEntity.ok(responseSignUpDto);
    }

    /**
     *
     * @param loginRequestDto
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<JwtToken> login(@RequestBody @Validated LoginRequestDto requestDto) {
        JwtToken jwtToken = userService.login(requestDto);
        return ResponseEntity.ok(jwtToken);
    }

//    /**
//     * 로그아웃
//     * @param request 세션 삭제
//     * @return
//     */
//
//    @PostMapping("/logout")
//    public ResponseEntity<String>  logout(@RequestBody @Valid LogOutDto logOutDto){
//        userService.logOut(logOutDto.getAccessToken(), logOutDto.getEmail());
//        return ResponseEntity.ok("로그아웃 완료");
//    }
}

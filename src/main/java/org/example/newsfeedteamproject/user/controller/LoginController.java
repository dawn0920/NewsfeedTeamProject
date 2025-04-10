package org.example.newsfeedteamproject.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.newsfeedteamproject.user.dto.LoginRequestDto;
import org.example.newsfeedteamproject.user.entity.User;
import org.example.newsfeedteamproject.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    /**
     * 로그인 API
     * @param requestDto 로그인 정보
     * @param request 세션 생성
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<Void> login(@Validated @RequestBody LoginRequestDto requestDto, HttpServletRequest request){

        Long userId = userService.login(requestDto);
        HttpSession session = request.getSession();
        session.setAttribute("LOGIN_USER", userId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 로그아웃
     * @param request 세션 삭제
     * @return
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request){

        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }

        return ResponseEntity.ok("로그아웃 성공");

    }
}

package org.example.newsfeedteamproject.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor

public enum ExceptionCode {
    FIND_NOT_INTERFACE(HttpStatus.BAD_REQUEST, "정보를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String errorMessage;
}

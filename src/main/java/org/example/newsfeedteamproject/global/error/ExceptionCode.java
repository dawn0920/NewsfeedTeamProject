package org.example.newsfeedteamproject.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor

public enum ExceptionCode implements ErrorCode {
    FIND_NOT_INTERFACE(HttpStatus.BAD_REQUEST, "정보를 찾을 수 없습니다."),
    VALID_EXCEPTION(HttpStatus.BAD_REQUEST, "VALID_EXCEPTION가 발생했습니다."),
    PASSWORD_NOT_FOUND(HttpStatus.UNAUTHORIZED, "패스워드가 일치하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String errorMessage;

    @Override
    public HttpStatus gethttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }
}

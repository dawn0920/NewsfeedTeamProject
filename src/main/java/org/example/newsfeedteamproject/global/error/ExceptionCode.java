package org.example.newsfeedteamproject.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor

public enum ExceptionCode implements ErrorCode {
    FIND_NOT_INTERFACE(HttpStatus.BAD_REQUEST, "정보를 찾을 수 없습니다."),
    VALID_EXCEPTION(HttpStatus.BAD_REQUEST, "VALID_EXCEPTION가 발생했습니다."),
    PASSWORD_NOT_FOUND(HttpStatus.UNAUTHORIZED, "패스워드가 일치하지 않습니다."),
    NO_EDIT_PERMISSION(HttpStatus.FORBIDDEN, "게시글 수정 권한이 없습니다."),
    NO_DELETE_PERMISSION(HttpStatus.FORBIDDEN, "게시글 삭제 권한이 없습니다."),
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다.");

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

package org.example.newsfeedteamproject.global.error;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice

public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorResponse> handleArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {

        log.error("[ValidException 발생] cause:{}, message: {}",
                NestedExceptionUtils.getMostSpecificCause(e),
                e.getMessage());

        ErrorCode errorCode = ExceptionCode.VALID_EXCEPTION;

        CustomErrorResponse response = CustomErrorResponse.builder()
                .message(errorCode.getMessage())
                .path(request.getRequestURI())
                .timeStamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }



}

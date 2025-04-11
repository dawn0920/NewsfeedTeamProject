package org.example.newsfeedteamproject.global.error;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder

public class CustomErrorResponse {
    private String message;
    private String path;
    private LocalDateTime timeStamp;

    public CustomErrorResponse(ExceptionCode exceptionErrorCode, String path, LocalDateTime timeStamp) {
        this.message = exceptionErrorCode.getMessage();
        this.path = path;
        this.timeStamp = LocalDateTime.now();
    }
}

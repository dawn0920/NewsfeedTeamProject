package org.example.newsfeedteamproject.global.error;
import org.springframework.http.HttpStatus;


public interface ErrorCode {
    HttpStatus gethttpStatus();
    String getMessage();
}

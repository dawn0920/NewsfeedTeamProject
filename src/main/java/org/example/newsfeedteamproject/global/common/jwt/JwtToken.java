package org.example.newsfeedteamproject.global.common.jwt;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor

public class JwtToken {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}

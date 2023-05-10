package com.company.job.myhasjobwithjwt.payload.auth;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Setter@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshTokenRequest {
    private String refreshToken;
}

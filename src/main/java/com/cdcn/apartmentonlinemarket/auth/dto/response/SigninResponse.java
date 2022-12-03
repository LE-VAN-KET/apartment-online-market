package com.cdcn.apartmentonlinemarket.auth.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class SigninResponse {
    private UUID userId;
    private String accessToken;
    private String tokenType;
    private Long expiresIn;
    private String refreshToken;
    private Long refreshExpiresIn;
}

package com.cdcn.apartmentonlinemarket.auth.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
@Setter
public class SignupResponse {
    private UUID userId;
    private String username;
    private String message;
}

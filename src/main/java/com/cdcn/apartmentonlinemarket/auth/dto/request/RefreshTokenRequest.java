package com.cdcn.apartmentonlinemarket.auth.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RefreshTokenRequest {
    @NotNull
    @NotBlank
    private String token;
    private String tokenType;
}
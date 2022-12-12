package com.cdcn.apartmentonlinemarket.auth.controller;

import com.cdcn.apartmentonlinemarket.auth.dto.request.RefreshTokenRequest;
import com.cdcn.apartmentonlinemarket.auth.dto.request.SigninRequest;
import com.cdcn.apartmentonlinemarket.auth.dto.request.SignupCommand;
import com.cdcn.apartmentonlinemarket.auth.dto.response.SigninResponse;
import com.cdcn.apartmentonlinemarket.auth.dto.response.SignupResponse;
import com.cdcn.apartmentonlinemarket.auth.service.AuthService;
import com.cdcn.apartmentonlinemarket.security.model.TokenPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public SignupResponse signUp(@Valid @RequestBody SignupCommand command) {
        return authService.signUp(command);
    }

    @PostMapping("/signin")
    public SigninResponse signIp(@Valid @RequestBody SigninRequest command) {
        return authService.signIn(command);
    }

    @PostMapping("/signup/seller")
    public SignupResponse signUpSeller(@Valid @RequestBody SignupCommand command) {
        return authService.signUpSeller(command);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenPair> refresh(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }

}

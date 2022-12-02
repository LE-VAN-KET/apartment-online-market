package com.cdcn.apartmentonlinemarket.auth.controller;

import com.cdcn.apartmentonlinemarket.auth.dto.request.SigninRequest;
import com.cdcn.apartmentonlinemarket.auth.dto.request.SignupCommand;
import com.cdcn.apartmentonlinemarket.auth.dto.response.SigninResponse;
import com.cdcn.apartmentonlinemarket.auth.dto.response.SignupResponse;
import com.cdcn.apartmentonlinemarket.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}

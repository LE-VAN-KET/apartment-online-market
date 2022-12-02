package com.cdcn.apartmentonlinemarket.auth.controller;

import com.cdcn.apartmentonlinemarket.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private AuthService authService;

}

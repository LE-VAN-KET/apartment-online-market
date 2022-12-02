package com.cdcn.apartmentonlinemarket.auth.service;

import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<String> checkToken();
}

package com.cdcn.apartmentonlinemarket.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{
    @Autowired
    AuthService authService;
    @Override
    public ResponseEntity<String> checkToken() {
        try{
            return authService.checkToken();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}

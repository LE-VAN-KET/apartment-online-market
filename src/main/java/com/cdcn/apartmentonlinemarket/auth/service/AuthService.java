package com.cdcn.apartmentonlinemarket.auth.service;

import com.cdcn.apartmentonlinemarket.auth.dto.request.SigninRequest;
import com.cdcn.apartmentonlinemarket.auth.dto.request.SignupCommand;
import com.cdcn.apartmentonlinemarket.auth.dto.response.SigninResponse;
import com.cdcn.apartmentonlinemarket.auth.dto.response.SignupResponse;

public interface AuthService {
    SignupResponse signUp(SignupCommand command);
    SignupResponse signUpSeller(SignupCommand command);
    SigninResponse signIn(SigninRequest request);
}

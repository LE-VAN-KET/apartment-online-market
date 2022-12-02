package com.cdcn.apartmentonlinemarket.auth.dto.request;

import com.cdcn.apartmentonlinemarket.auth.util.validator.ValidPassword;
import com.cdcn.apartmentonlinemarket.auth.util.validator.ValidUsername;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class SigninRequest {
    @NotNull
    @NotEmpty(message = "Username is required")
    @ValidUsername
    private String username;

    @NotNull
    @NotEmpty(message = "Password is required")
    @ValidPassword
    private String password;
}

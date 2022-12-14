package com.cdcn.apartmentonlinemarket.auth.dto.request;

import com.cdcn.apartmentonlinemarket.auth.util.validator.PasswordMatches;
import com.cdcn.apartmentonlinemarket.auth.util.validator.ValidPassword;
import com.cdcn.apartmentonlinemarket.auth.util.validator.ValidUsername;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
@PasswordMatches
public class SignupCommand {
    @NotNull
    @NotEmpty
    @ValidUsername
    private String username;

    @NotNull
    @NotEmpty
    @Size(min = 6, max = 20)
    @ValidPassword
    private String password;

    @NotNull
    @NotEmpty
    private String confirmPassword;

    @Email
    private String mailNotification;
}

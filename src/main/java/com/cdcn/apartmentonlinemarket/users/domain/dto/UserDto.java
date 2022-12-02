package com.cdcn.apartmentonlinemarket.users.domain.dto;

import com.cdcn.apartmentonlinemarket.common.enums.UserPrioty;
import com.cdcn.apartmentonlinemarket.common.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
    private int id;

    private String user;

    private String password;

    private UserPrioty userprority;

    private String salt;

    private UserStatus status;

    private Boolean is_delete;

    private String mail_notification;

    private String token;

}

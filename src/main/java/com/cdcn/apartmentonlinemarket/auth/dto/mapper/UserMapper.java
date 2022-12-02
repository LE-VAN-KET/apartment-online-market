package com.cdcn.apartmentonlinemarket.auth.dto.mapper;

import com.cdcn.apartmentonlinemarket.auth.dto.request.SignupCommand;
import com.cdcn.apartmentonlinemarket.auth.dto.response.SignupResponse;
import com.cdcn.apartmentonlinemarket.users.domain.entity.Users;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public Users convertSignupCommandToUser(SignupCommand command) {
        Users users = new Users();
        if (command != null) {
            BeanUtils.copyProperties(command, users);
        }
        return users;
    }
    
}

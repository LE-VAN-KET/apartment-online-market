package com.cdcn.apartmentonlinemarket.domain.mapper;

import com.cdcn.apartmentonlinemarket.domain.dto.UserDto;
import com.cdcn.apartmentonlinemarket.domain.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDataMapper extends BaseMapper<Users, UserDto> {
    @Override
    public Users convertToEntity(UserDto dto, Object... args) {
        return null;
    }

    @Override
    public UserDto convertToDto(Users entity, Object... args) {
        return null;
    }

    // custom mapper data response
}

package com.cdcn.apartmentonlinemarket.users.domain.mapper;

import com.cdcn.apartmentonlinemarket.infrastructure.domain.mapper.BaseMapper;
import com.cdcn.apartmentonlinemarket.users.domain.dto.UserDto;
import com.cdcn.apartmentonlinemarket.users.domain.entity.Users;
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

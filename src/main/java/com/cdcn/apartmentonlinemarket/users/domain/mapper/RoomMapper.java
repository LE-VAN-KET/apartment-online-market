package com.cdcn.apartmentonlinemarket.users.domain.mapper;

import com.cdcn.apartmentonlinemarket.infrastructure.domain.mapper.BaseMapper;
import com.cdcn.apartmentonlinemarket.users.domain.dto.RoomDto;
import com.cdcn.apartmentonlinemarket.users.domain.entity.Rooms;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper extends BaseMapper<Rooms, RoomDto> {
    @Override
    public Rooms convertToEntity(RoomDto dto, Object... args) {
        return null;
    }

    @Override
    public RoomDto convertToDto(Rooms entity, Object... args) {
        RoomDto roomDto = new RoomDto();
        if (entity != null) {
            BeanUtils.copyProperties(entity, roomDto);
        }
        return roomDto;
    }
}

package com.cdcn.apartmentonlinemarket.users.domain.mapper;

import com.cdcn.apartmentonlinemarket.infrastructure.domain.mapper.BaseMapper;
import com.cdcn.apartmentonlinemarket.users.domain.dto.RoomDto;
import com.cdcn.apartmentonlinemarket.users.domain.dto.StageDto;
import com.cdcn.apartmentonlinemarket.users.domain.entity.Stages;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class StageMapper extends BaseMapper<Stages, StageDto> {
    private RoomMapper roomMapper;

    public StageMapper() {
        roomMapper = new RoomMapper();
    }

    @Override
    public Stages convertToEntity(StageDto dto, Object... args) {
        return null;
    }

    @Override
    public StageDto convertToDto(Stages entity, Object... args) {
        StageDto stageDto = new StageDto();
        if (entity != null) {
            BeanUtils.copyProperties(entity, stageDto);
            if (entity.getRoomsSet() != null && !entity.getRoomsSet().isEmpty()) {
                List<RoomDto> roomDtoList = roomMapper.convertToDtoList(entity.getRoomsSet());
                stageDto.setRoomDtoList(roomDtoList);
            }
        }
        return stageDto;
    }
}

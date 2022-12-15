package com.cdcn.apartmentonlinemarket.users.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoomDto {
    private Long id;
    private Integer number;
    private String location;

    private String citizenId;
}

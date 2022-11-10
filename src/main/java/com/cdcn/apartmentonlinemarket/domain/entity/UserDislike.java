package com.cdcn.apartmentonlinemarket.domain.entity;

import com.cdcn.apartmentonlinemarket.common.enums.DislikeType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "user_dislike")
@Getter
@Setter
@NoArgsConstructor
public class UserDislike {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @NotNull
    private UUID userId;

    @Enumerated(EnumType.STRING)
    private DislikeType dislikeType;

    private UUID dislikeId;
}

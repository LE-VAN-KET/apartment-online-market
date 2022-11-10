package com.cdcn.apartmentonlinemarket.domain.entity;

import com.cdcn.apartmentonlinemarket.common.enums.RecommentForType;
import com.cdcn.apartmentonlinemarket.common.enums.RecommentStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "recomments")
@Getter
@Setter
@NoArgsConstructor
public class Recomment {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    private UUID userId;
    private String content;

    @Enumerated(EnumType.STRING)
    private RecommentForType forType;

    private UUID forId;

    private OffsetDateTime postDate;

    @Min(0)
    @Max(5)
    private BigDecimal star;

    @Enumerated(EnumType.STRING)
    private RecommentStatus status;
}

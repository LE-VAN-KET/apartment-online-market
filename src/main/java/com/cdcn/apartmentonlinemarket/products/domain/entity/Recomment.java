package com.cdcn.apartmentonlinemarket.products.domain.entity;

import com.cdcn.apartmentonlinemarket.common.enums.RecommentForType;
import com.cdcn.apartmentonlinemarket.common.enums.RecommentStatus;
import com.cdcn.apartmentonlinemarket.infrastructure.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@EntityListeners(AuditingEntityListener.class)
public class Recomment extends BaseEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(columnDefinition = "BINARY(16)")
    private UUID userId;
    private String content;

    @Enumerated(EnumType.STRING)
    private RecommentForType forType;

    @Column(columnDefinition = "BINARY(16)")
    private UUID forId;

    private OffsetDateTime postDate;

    @Min(0)
    @Max(5)
    private BigDecimal star;

    @Enumerated(EnumType.STRING)
    private RecommentStatus status;
}

package com.cdcn.apartmentonlinemarket.products.domain.entity;

import com.cdcn.apartmentonlinemarket.common.enums.ContainerType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;


@Entity
@Table(name = "attachments")
@Getter
@Setter
@NoArgsConstructor
public class Attachments {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(unique = true, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    private String fileName;

    @Enumerated(EnumType.STRING)
    private ContainerType containerType;

    @Column(columnDefinition = "BINARY(16)")
    private UUID containerId;

    private Integer sort;
    private String link;

    @Column(length = 50)
    private String contentType;
    private BigDecimal size;

    private Boolean allowDownload;
}

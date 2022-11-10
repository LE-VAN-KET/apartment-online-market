package com.cdcn.apartmentonlinemarket.domain.entity;

import com.cdcn.apartmentonlinemarket.common.enums.StoreStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "stores")
@Getter
@Setter
@NoArgsConstructor
public class Store {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(unique = true, nullable = false)
    private UUID id;

    private String name;
    private String description;
    private UUID ownerId;
    private Date establishDate;
    private BigDecimal star;

    @Enumerated(EnumType.STRING)
    private StoreStatus status;

    @OneToMany(mappedBy = "store")
    private Set<Product> products;

    @OneToMany(mappedBy = "store")
    private Set<StorePaymentGateway> storePaymentGateways;
}

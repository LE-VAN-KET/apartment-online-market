package com.cdcn.apartmentonlinemarket.stores.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "store_payment_gateway")
@Getter
@Setter
@NoArgsConstructor
public class StorePaymentGateway {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(unique = true, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    private String gatewayName;
    private Boolean enable;
    private String logo;
    private Integer position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;
}

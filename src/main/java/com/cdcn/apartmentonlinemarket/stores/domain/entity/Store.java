package com.cdcn.apartmentonlinemarket.stores.domain.entity;

import com.cdcn.apartmentonlinemarket.common.enums.StoreStatus;
import com.cdcn.apartmentonlinemarket.products.domain.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
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
    @Column(unique = true, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    private String name;
    private String description;

    @Column(columnDefinition = "BINARY(16)")
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

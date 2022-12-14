package com.cdcn.apartmentonlinemarket.orders.domain.entity;

import com.cdcn.apartmentonlinemarket.common.enums.OrderStatus;
import com.cdcn.apartmentonlinemarket.infrastructure.domain.entity.BaseEntity;
import com.cdcn.apartmentonlinemarket.users.domain.entity.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.security.Timestamp;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Orders extends BaseEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    private String reference;
    private Instant expiredAt;

    @Enumerated(EnumType.ORDINAL)
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;
//
//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
//    @JoinColumn(name = "order_id", nullable = false)
//    private Set<OrderItem> orderItems;

    public String generateReference(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

}
